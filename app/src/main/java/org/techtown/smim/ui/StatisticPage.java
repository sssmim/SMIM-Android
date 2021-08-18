package org.techtown.smim.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.techtown.smim.R;
import org.techtown.smim.database.ietime;
import org.techtown.smim.database.reservation;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatisticPage extends Fragment {
    LineChart mpLineChart;
    int po=0;
    public List<ietime> list = new ArrayList<>();


    ArrayList<Entry> dataVals = new ArrayList<Entry>();
    LineDataSet lineDataSet1;

    ArrayList<ILineDataSet> dataSets;

    LineData data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_statistic_page, container, false);

        Spinner s = root.findViewById(R.id.spinner1);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
               po=position+1;
                mpLineChart = root.findViewById(R.id.lineChart);

                dataVals.clear();
                RequestQueue requestQueue;
                // Instantiate the cache
                Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
                // Set up the network to use HttpURLConnection as the HTTP client.
                Network network = new BasicNetwork(new HurlStack());
                // Instantiate the RequestQueue with the cache and network.
                requestQueue = new RequestQueue(cache, network);
                // Start the queue
                requestQueue.start();


                String url = "http://52.78.235.23:8080/ietime";

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        // 한글깨짐 해결 코드
                        String changeString = new String();

                        try {
                            changeString = new String(response.getBytes("8859_1"), "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        Gson gson = new GsonBuilder().setPrettyPrinting().create();

                        Type listType = new TypeToken<ArrayList<ietime>>() {
                        }.getType();

                        list = gson.fromJson(changeString, listType);

                        Log.e("datetest", list.get(0).daily_total.toString());

                        String realmon;
                        Integer m;
                        String realday;
                        Integer d;
                        Integer count = 0;
                        for (int i = 0; i < list.size(); i++) {

                            Date date = list.get(i).daily_record;
                            Log.e("datetest", list.get(0).daily_record.toString());

                            SimpleDateFormat f = new SimpleDateFormat("MM");
                            String getmon = f.format(date);
                            Log.e("datetest", getmon);

                            if (getmon.substring(0, 1) == "0") {
                                realmon = getmon.substring(1, 2);
                                m = Integer.parseInt(realmon);
                            } else {
                                m = Integer.parseInt(getmon);
                            }
                            Log.e("m", m.toString());

                            if (m.equals(po)) {
                                Log.e("m1", m.toString());
                                Date day = list.get(i).daily_record;

                                SimpleDateFormat f1 = new SimpleDateFormat("dd");
                                String getday = f1.format(date);

                                if (getday.substring(0, 1) == "0") {
                                    realday = getmon.substring(1, 2);
                                    d = Integer.parseInt(realday);
                                } else {
                                    d = Integer.parseInt(getday);
                                }
                                Log.e("d", d.toString());


                                String time = list.get(i).daily_total;
                                Log.e("time1", time);
                                String str = time;
                                Date ddd = null;
                                SimpleDateFormat simpleYear2 = new SimpleDateFormat("HH:mm:ss");

                                try {
                                    ddd = simpleYear2.parse(str);
                                } catch (ParseException e) {
                                }
                                Log.e("time2", ddd.toString());

                                Long a = ddd.getTime();
                                Long minutes = a / 1000 / 60;
                                Log.e("time3", minutes.toString());
                                //if(count==0){
                                // dataVals.remove(0);
                                //mpLineChart.invalidate();}

                                dataVals.add(new Entry(d, minutes));
                                Log.e("size", String.valueOf(dataVals.size()));
                                lineDataSet1 = new LineDataSet(dataVals, "월별개인기록");

                                dataSets = new ArrayList<>();
                                dataSets.add(lineDataSet1);
                                Log.e("size1", String.valueOf(dataSets.size()));
                                data = new LineData(dataSets);
                                mpLineChart.setData(data);
                                mpLineChart.invalidate();
                            }


                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Dd","feee");
                    }
                });

                // Add the request to the RequestQueue.
                requestQueue.add(stringRequest);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });















        return root;
    }

}