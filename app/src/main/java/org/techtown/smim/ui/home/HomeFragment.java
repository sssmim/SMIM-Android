package org.techtown.smim.ui.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.techtown.smim.R;
import org.techtown.smim.database.gexercise;
import org.techtown.smim.database.iexercise;
import org.techtown.smim.database.personal;
import org.techtown.smim.database.reservation;
import org.techtown.smim.ui.dashboard.Exercise;
import org.techtown.smim.ui.dashboard.ExercisePlan;
import org.techtown.smim.ui.dashboard.FindGroup;
import org.techtown.smim.ui.notifications.CustomExercise;
import org.techtown.smim.ui.notifications.CustomExerciseAdapter;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    String year_m;
    String ryear;
    String rmonth;
    String rday;
    String rselect;
    Long mem_num;
    public List<reservation> list3 = new ArrayList<>();
    public List<Long> ge_numlist = new ArrayList<>();
    public List<gexercise> glist = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Bundle bundle = getArguments();
        mem_num = bundle.getLong("mem_num");
        Log.d("test_HomeFragment", String.valueOf(mem_num));

        TextView textView01 = (TextView) root.findViewById(R.id.textView1);

        long Now = System.currentTimeMillis();
        Date mDate = new Date(Now);

        SimpleDateFormat simpleYear = new SimpleDateFormat("yyyy.M");
        String getYear = simpleYear.format(mDate);
        SimpleDateFormat simpleDay = new SimpleDateFormat("dd (EE)", Locale.getDefault());
        String getDay = simpleDay.format(Now);
        textView01.setText(getYear);
 //Date rselect=2021-08-12;
        RecyclerView recyclerView3 = (RecyclerView) root.findViewById(R.id.RecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView3.setLayoutManager(layoutManager);
        ActivityListAdapter adapter = new ActivityListAdapter();
        /*org.techtown.smim.ui.home.ActivityListAdapter adapter = new org.techtown.smim.ui.home.ActivityListAdapter();

        adapter.addItem(new org.techtown.smim.ui.home.ActivityList("스쿼트", "10:00 ~ 12:00","10:00 ~ 12:00"));
        adapter.addItem(new org.techtown.smim.ui.home.ActivityList("런지", "14:00 ~ 17:00","10:00 ~ 12:00"));
        adapter.addItem(new org.techtown.smim.ui.home.ActivityList("무야호", "15:00 ~ 19:00","10:00 ~ 12:00"));

        recyclerView.setAdapter(adapter);*/

        CalendarView calendar = (CalendarView) root.findViewById(R.id.calendarView);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
              year_m = year + "." + (month + 1);
                ryear=year+"";
                rmonth=month+1+"";
                rday = dayOfMonth+"";
                rselect = ryear+"-"+rmonth+"-"+rday;
                textView01.setText(year_m);

            }
        });


        RequestQueue requestQueue;
        // Instantiate the cache
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());
        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);
        // Start the queue
        requestQueue.start();


        String url = "http://52.78.235.23:8080/reservation";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                // 한글깨짐 해결 코드
                String changeString = new String();
                try {
                    changeString = new String(response.getBytes("8859_1"),"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Type listType = new TypeToken<ArrayList<reservation>>(){}.getType();
                list3 = gson.fromJson(changeString, listType);



                for(int i = 0; i< list3.size(); i++) {
                    if (mem_num.compareTo(list3.get(i).pnum) == 0) {
                        ge_numlist.add(list3.get(i).gnum);
                        //Log.d("test_if","ifff");

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





        String url1 = "http://52.78.235.23:8080/gexercise";

        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                // 한글깨짐 해결 코드
                String changeString = new String();
                try {
                    changeString = new String(response.getBytes("8859_1"),"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Type listType = new TypeToken<ArrayList<gexercise>>(){}.getType();
                glist = gson.fromJson(changeString, listType);



                for(int i = 0; i< ge_numlist.size(); i++) {
                    for(int j = 0; j< glist.size(); j++) {
                    if ( ge_numlist.get(i).compareTo(glist.get(j).ge_num) == 0) {
                        //if(glist.get(j).ge_date.equals(rselect)) {
                            adapter.addItem(new ActivityList(glist.get(j).ge_desc, glist.get(j).ge_start_time, glist.get(j).ge_end_time));
                        //}
                    }
                }}

                recyclerView3.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Dd","fail");
            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest1);






        return root;
    }


}