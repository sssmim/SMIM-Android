package org.techtown.smim.ui.notifications;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TimerFragment extends Fragment {

    private TextView exercise_name;
    private TextView countdownText;
    private TextView secText;
    private ToggleButton start_stop;

    private CountDownTimer countDownTimer;

    private long time = 0;
    private long tempTime = 0;

    private Integer index = 0;

    private boolean firstState = true;

    public List<ielist> list = new ArrayList<>();
    public List<iexercise> list2 = new ArrayList<>();
    public List<String> nameList = new ArrayList<>();
    public List<Integer> countList = new ArrayList<>();
    public List<String> secList = new ArrayList<>();

    Long mem_num;

    public int prevnext = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
       // setContentView(R.layout.timer);
        View view = inflater.inflate(R.layout.timer,container,false);

        Bundle bundle = getArguments();
        if(bundle != null) {
            mem_num = bundle.getLong("mem_num");
        }

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String url2 = "http://52.78.235.23:8080/iexercise";

        secText =  (TextView)view.findViewById(R.id.secTextView);

        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
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
                list2 = gson.fromJson(changeString, listType);

                for (int i = 0; i < list2.size(); i++) {
                    secList.add(list2.get(i).ie_sec);
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

        String url = "http://52.78.235.23:8080/list";


        exercise_name =  (TextView)view.findViewById(R.id.exercisename);
        countdownText =  (TextView)view.findViewById(R.id.countTextView);

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
                Type listType = new TypeToken<ArrayList<ielist>>() {
                }.getType();
                list = gson.fromJson(changeString, listType);

                for (int i = 0; i < list.size(); i++) {
                    nameList.add(list.get(i).name1);
                    nameList.add(list.get(i).name2);
                    nameList.add(list.get(i).name3);
                    nameList.add(list.get(i).name4);
                    nameList.add(list.get(i).name5);
                }

                if (nameList.size() != 0) {
                    exercise_name.setText(nameList.get(0));
                }

                for (int i = 0; i < list.size(); i++) {
                    countList.add(list.get(i).count1);
                    countList.add(list.get(i).count2);
                    countList.add(list.get(i).count3);
                    countList.add(list.get(i).count4);
                    countList.add(list.get(i).count5);
                }

                if (countList.size() != 0) {
                    countdownText.setText(Integer.toString(countList.get(0)));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(stringRequest);

        requestQueue.add(stringRequest1);

        Button btnComplete =  (Button) view.findViewById(R.id.btnComplete);

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                show();

            }
        });

        Button button =  (Button) view.findViewById(R.id.gonext);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int siz = nameList.size();
                if (prevnext < siz-1) { //마지막 다음 버튼 눌렀을때 index가 size랑 같아지는 문제발생,,추후수정
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
                    prevnext++;
                }
            }
        });

        Button button2 =  (Button) view.findViewById(R.id.goprev);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prevnext > 0) {
                    index--;

                    if (nameList.size() != 0) {
                        exercise_name.setText(nameList.get(index));
                    }
                    if (countList.size() != 0) {
                        countdownText.setText(Integer.toString(countList.get(index)));
                    }
                    if (secList.size() != 0) {
                        secText.setText(secList.get(index));
                    }
                    prevnext--;
                }
            }
        });

        start_stop =  (ToggleButton) view.findViewById(R.id.start_stop);
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

        return view;
    }


    void show(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("마일리지 500점 획득!"); //나중에 DB연결 필요


        builder.setNegativeButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //   Intent intent = new Intent(getApplicationContext(), CustomExerciseMerge.class); //크롤링 초기화면으로 돌아가려하면 오류
                        //  startActivity(intent);

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        CrawlingPage crawlfragment = new CrawlingPage();
                        Bundle bundle = new Bundle();
                        bundle.putLong("mem_num",mem_num);
                        crawlfragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.container, crawlfragment);

                        fragmentTransaction.commit();

                        //Fragment fragment = new CrawlingPage();
                        //FragmentManager fm = ((MainActivity) mContext).getSupportFragmentManager();
                        //FragmentTransaction ft = fm.beginTransaction();
                        //ft.add(R.id., fragment);
                        //ft.commit();
                    }
                });
        builder.show();
    }


}
