package org.techtown.smim.ui.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

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

import org.techtown.smim.MainActivity;
import org.techtown.smim.R;
import org.techtown.smim.database.personal;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class LoginActivity_checkPwd extends AppCompatActivity {

    private ArrayList<personal> list = new ArrayList<>();

    private EditText getPwd;
    private Button checkPwd;

    private boolean check = false;

    Dialog dialog;

    Long mem_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_check);

        Intent intent1 = getIntent();
        mem_num = intent1.getLongExtra("mem_num", 0L);

        getPwd = findViewById(R.id.getPwd);
        checkPwd = findViewById(R.id.checkPwd);

        checkPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Pwd = getPwd.getText().toString();

                RequestQueue requestQueue;
                Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
                Network network = new BasicNetwork(new HurlStack());
                requestQueue = new RequestQueue(cache, network);
                requestQueue.start();

                String url = "http://52.78.235.23:8080/personal";

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
                        Type listType = new TypeToken<ArrayList<personal>>() {
                        }.getType();
                        list = gson.fromJson(changeString, listType);

                        for(int i=0; i<list.size(); i++) {
                            if(mem_num.compareTo(list.get(i).mem_num) == 0) {
                                if(Pwd.compareTo(list.get(i).pwd) == 0) {
                                    check = true;
                                }
                                break;
                            }
                        }

                        if(check == false) {
                            dialog = new Dialog(LoginActivity_checkPwd.this);       // Dialog 초기화
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                            dialog.setContentView(R.layout.login_checkpwd_dialog);
                            dialog.show();
                            Button cancel = dialog.findViewById(R.id.btnCancel3);
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss(); // 다이얼로그 닫기
                                }
                            });
                        } else {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("mem_num" , mem_num);
                            startActivity(intent);
                            finish();
                        }
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

