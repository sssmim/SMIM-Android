package org.techtown.smim.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.techtown.smim.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExercisePlanFragment extends Fragment {

    public static final int plantoyoutbe = 102;
    public static final int plantomain = 10;

    public String youtubeurl;
    public TextView days;
    public TextView startHour;
    public TextView startMin;
    public TextView endHour;
    public TextView endMin;
    public TextView planMemo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_exercise_plan, container, false);

       // Intent getIntent = getIntent();
        //if(getIntent != null){
          //  String value = getIntent.getStringExtra("key");
            //Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG).show();  //에러가 뜹니다..?
           // youtubeurl = value;}

        days = view.findViewById(R.id.days);
        startHour = view.findViewById(R.id.starthour);
        startMin = view.findViewById(R.id.startmin);
        endHour = view.findViewById(R.id.endthour);
        endMin = view.findViewById(R.id.endmin);
        planMemo = view.findViewById(R.id.planmemo);

        CalendarView calendar = (CalendarView) view.findViewById(R.id.calendarView);
        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
        Date time = new Date();

        String time1 = format1.format(time);
        days.setText(time1);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String setDay = year + "-" + (month+1) + "-" + dayOfMonth;
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(setDay);
                    String newString = new SimpleDateFormat("yyyy-MM-dd").format(date);
                    days.setText(newString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        Button button = view.findViewById(R.id.chooseyoutube);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), YoutubePlan.class);
                //startActivityForResult(intent, plantoyoutbe);
                /*FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
                DashboardFragment f = new DashboardFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Obj", "positio");
                f.setArguments(bundle);
                transaction.replace(R.id.container,f);
                transaction.commit();*/
            }
        });

        Button button1 = view.findViewById(R.id.addplan);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://52.78.235.23:8080/gexercise";
                Map map = new HashMap();
                map.put("ge_date", days.getText().toString());
                String start_time = startHour.getText().toString() + ":" + startMin.getText().toString() + ":00";
                map.put("ge_start_time", start_time);
                String end_time = endHour.getText().toString() + ":" + endMin.getText().toString() + ":00";
                map.put("ge_end_time", end_time);
                map.put("ge_run_time", "08:48:00"); // run_time이 필요한가? 각자 실행한 시간이 다를텐데??
                map.put("ge_desc", planMemo.getText().toString());
                map.put("video_url", youtubeurl);
                JSONObject params = new JSONObject(map);

                JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject obj) {
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }) {

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=UTF-8";
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                queue.add(objectRequest);
                Toast.makeText(getActivity().getApplicationContext(), "추가되었습니다", Toast.LENGTH_LONG).show();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                DashboardFragment fragment2 = new DashboardFragment();
                transaction.replace(R.id.container, fragment2);
                transaction.commit();



            }
        });










        return view;
    }
}