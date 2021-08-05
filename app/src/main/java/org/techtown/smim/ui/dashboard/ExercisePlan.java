package org.techtown.smim.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

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

    public String setDay;
    public String youtubeurl;
    public TextView textView1;
    public TextView textView2;
    public TextView textView3;
    public TextView textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_plan);
        Intent getIntent = getIntent();
        if(getIntent!=null){
        String value = getIntent.getStringExtra("key");
        Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG).show();
        youtubeurl = value;}


        textView1 = findViewById(R.id.days);
        textView2 = findViewById(R.id.starthour);
        textView3 = findViewById(R.id.endthour);
        textView4 = findViewById(R.id.planmemo);

        CalendarView calendar = (CalendarView) findViewById(R.id.calendarView);
        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
        Date time = new Date();

        String time1 = format1.format(time);
        textView1.setText(time1);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String setDay = year + "-" + (month+1) + "-" + dayOfMonth;
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(setDay);
                    String newString = new SimpleDateFormat("yyyy-MM-dd").format(date);
                    textView1.setText(newString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        //textView1.setText(textView2.getText());  << 선택 날짜 포스트 하는 방법임.

        Button button = findViewById(R.id.chooseyoutube);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), YoutubePlan.class);
                startActivityForResult(intent,plantoyoutbe);
            }
        });

        Button button1 = findViewById(R.id.addplan);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://52.78.235.23:8080/gexercise";
                Map map = new HashMap();
                map.put("ge_name", "test");
                map.put("ge_desc", textView4.getText().toString());
                map.put("ge_date", textView1.getText().toString());
                map.put("ge_start_time", textView2.getText().toString() + ":00:00");
                map.put("ge_end_time", textView3.getText().toString() + ":00:00");
                map.put("ge_run_time", "08:48:00");
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
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(objectRequest);
                //Toast.makeText(getApplicationContext(), "추가되었습니다", Toast.LENGTH_LONG).show();
                //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //DashboardFragment fragment2 = new DashboardFragment();
                //transaction.replace(R.id.container, fragment2);
                //transaction.commit();

            }
        });
    }
}
