package org.techtown.smim.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

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

//eee
public class ExercisePlan extends AppCompatActivity {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_exercise_plan);

        Intent getIntent = getIntent();
        if(getIntent != null){
        String value = getIntent.getStringExtra("key");
        //Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG).show();  //에러가 뜹니다..?
        youtubeurl = value;}

        days = findViewById(R.id.days);
        startHour = findViewById(R.id.starthour);
        startMin = findViewById(R.id.startmin);
        endHour = findViewById(R.id.endthour);
        endMin = findViewById(R.id.endmin);
        planMemo = findViewById(R.id.planmemo);

        CalendarView calendar = (CalendarView) findViewById(R.id.calendarView);
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

        Button button = findViewById(R.id.chooseyoutube);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(), YoutubePlan.class);
               startActivityForResult(intent, plantoyoutbe);
            }
        });

        Button button3 = findViewById(R.id.addplan1);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
                DashboardFragment f1 = new DashboardFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Obj", "positio");
                f1.setArguments(bundle);
                transaction.replace(R.id.container,f1);
                transaction.commit();*/

                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                DashboardFragment f= new DashboardFragment();
                transaction1.replace(R.id.container, f);
                transaction1.commit();
            }
        });



        Button button1 = findViewById(R.id.addplan);
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
                //map.put("ge_run_time", "08:48:00"); // run_time이 필요한가? 각자 실행한 시간이 다를텐데??
                map.put("ge_desc", planMemo.getText().toString());
                map.put("video_url", "youtubeurl");
                map.put("group_num", 2);
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
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(objectRequest);
                Toast.makeText(getApplicationContext(), "추가되었습니다", Toast.LENGTH_LONG).show();
                //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
               // DashboardFragment fragment2 = new DashboardFragment();
                //transaction.replace(R.id.container, fragment2);
                //transaction.commit();
                //finish();
                //finish();
                //finish();

            }
        });
    }
}
