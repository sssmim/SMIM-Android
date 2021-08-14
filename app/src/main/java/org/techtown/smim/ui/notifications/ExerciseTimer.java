package org.techtown.smim.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import org.techtown.smim.database.ielist;
import org.techtown.smim.database.iexercise;


public class ExerciseTimer extends AppCompatActivity {

    private TextView exercise_name;
    private TextView countdownText;
    private TextView secText;
    private ToggleButton start_stop;

    private CountDownTimer countDownTimer;

    private long time = 0;
    private long tempTime = 0;

    private Integer index = 0;
    private Integer Max = 0;
    private Long last = 0L;

    private boolean firstState = true;

    public List<iexercise> list = new ArrayList<>();
    public List<ielist> list2 = new ArrayList<>();
    public List<String> nameList = new ArrayList<>();
    public List<Integer> countList = new ArrayList<>();
    public List<String> ie_nameList = new ArrayList<>();
    public List<String> ie_secList = new ArrayList<>();
    public List<String> secList = new ArrayList<>();

    Long mem_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        Intent getIntent = getIntent();
        if(getIntent != null){
            mem_num = getIntent.getLongExtra("mem_num", 0L);
        }

        secText = findViewById(R.id.secTextView);
        exercise_name = findViewById(R.id.exercisename);
        countdownText = findViewById(R.id.countTextView);

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String url = "http://52.78.235.23:8080/iexercise";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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
                Type listType = new TypeToken<ArrayList<iexercise>>() {
                }.getType();
                list = gson.fromJson(changeString, listType);

                for (int i = 0; i < list.size(); i++) {
                    ie_nameList.add(list.get(i).ie_name);
                    ie_secList.add(list.get(i).ie_sec);
                }

                String url2 = "http://52.78.235.23:8080/list";

                StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
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
                        Type listType = new TypeToken<ArrayList<ielist>>() {
                        }.getType();
                        list2 = gson.fromJson(changeString, listType);

                        last = list2.get(list2.size() - 1).list_num;

                        for (int i = 0; i < list2.size(); i++) {
                            nameList.add(list2.get(i).name1);
                            if(list2.get(i).name2 != null) {
                                nameList.add(list2.get(i).name2);
                            }
                            if(list2.get(i).name3 != null) {
                                nameList.add(list2.get(i).name3);
                            }
                            if(list2.get(i).name4 != null) {
                                nameList.add(list2.get(i).name4);
                            }
                            if(list2.get(i).name5 != null) {
                                nameList.add(list2.get(i).name5);
                            }
                        }

                        Max = nameList.size();

                        if (nameList.size() != 0) {
                            exercise_name.setText(nameList.get(0));
                        }

                        for (int i = 0; i < list.size(); i++) {
                            countList.add(list2.get(i).count1);
                            if(list2.get(i).count2 != null) {
                                countList.add(list2.get(i).count2);
                            }
                            if(list2.get(i).count3 != null) {
                                countList.add(list2.get(i).count3);
                            }
                            if(list2.get(i).count4 != null) {
                                countList.add(list2.get(i).count4);
                            }
                            if(list2.get(i).count5 != null) {
                                countList.add(list2.get(i).count5);
                            }
                        }

                        if (countList.size() != 0) {
                            countdownText.setText(Integer.toString(countList.get(0)));
                        }

                        for(int i=0; i<nameList.size(); i++) {
                            for(int j=0; j<ie_nameList.size(); j++) {
                                if(nameList.get(i).compareTo(ie_nameList.get(j)) == 0) {
                                    secList.add(ie_secList.get(j));
                                }
                            }
                        }

                        if (secList.size() != 0) {
                            secText.setText(secList.get(0));
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

                requestQueue.add(stringRequest2);

                /*
                if (secList.size() != 0) {
                    secText.setText(secList.get(0));
                }*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(stringRequest);

        /*
        RequestQueue requestQueue2;
        Cache cache2 = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network2 = new BasicNetwork(new HurlStack());
        requestQueue2 = new RequestQueue(cache, network);
        requestQueue2.start();
        */


        Button button = findViewById(R.id.gonext);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                Log.d("test_Max", String.valueOf(Max));
                Log.d("test_index+1", String.valueOf(index+1));
                Log.d("test_compare", String.valueOf(Max.compareTo(index+1)));
                if(Max.compareTo(index+1) == 0) {
                    Log.d("test_finish", "stop");
                }
                if (nameList.size() != 0) {
                    exercise_name.setText(nameList.get(index));
                }
                if (countList.size() != 0) {
                    countdownText.setText(Integer.toString(countList.get(index)));
                }
                if (secList.size() != 0) {
                    secText.setText(secList.get(index));
                }
            }
        });

        start_stop = (ToggleButton) findViewById(R.id.start_stop);
        start_stop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled -> start
                    start_stop.setBackgroundDrawable(
                            getResources().
                                    getDrawable(R.drawable.red)
                    );

                    if (firstState) {
                        String second = secText.getText().toString();
                        time = (Long.parseLong(second) * 1000) + 1000;
                        secText.setText(Long.toString(time));
                    } else {
                        time = tempTime;
                    }
                    countDownTimer = new CountDownTimer(time, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            tempTime = millisUntilFinished;
                            updateTimer();
                        }

                        @Override
                        public void onFinish() {
                        }
                    }.start();
                    firstState = false;
                } else {
                    // The toggle is disabled -> pause
                    start_stop.setBackgroundDrawable(
                            getResources().
                                    getDrawable(R.drawable.yellow)
                    );
                    countDownTimer.cancel();
                }
            }

            private void updateTimer() {
                int seconds = (int) tempTime % 3600000 % 60000 / 1000;

                if (seconds == 0) {
                    Integer temp = countList.get(index)- 1;
                    countList.set(index, temp);
                    Integer inte = countList.get(index);
                    countdownText.setText(Integer.toString(inte));
                    seconds = Integer.parseInt(secList.get(index));
                    countDownTimer.start();
                }
                if (countList.get(index) < 0) {
                    index++;
                    if (nameList.size() != 0) {
                        exercise_name.setText(nameList.get(index));
                    }
                    if (countList.size() != 0) {
                        countdownText.setText(Integer.toString(countList.get(index)));
                    }
                    if (secList.size() != 0) {
                        secText.setText(secList.get(index));
                    }

                }

                secText.setText(Integer.toString(seconds));
            }
        });

        Button complete = findViewById(R.id.btnComplete);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://52.78.235.23:8080/list/" + Long.toString(last);

                StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

                requestQueue.add(stringRequest);
            }
        });
    }
}
