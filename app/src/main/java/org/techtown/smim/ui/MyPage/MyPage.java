package org.techtown.smim.ui.MyPage;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.techtown.smim.R;
import org.techtown.smim.database.personal;
import org.techtown.smim.ui.login.LoginActivity;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyPage extends Fragment {

    public List<personal> list = new ArrayList<>();

    String  Id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_my_page, container, false);

        Bundle bundle = getArguments();
        Long mem_num = bundle.getLong("mem_num");

        ViewGroup layout1 = (ViewGroup) root.findViewById(R.id.changePwd);
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                ChangePwd fragment = new ChangePwd();
                Bundle bundle = new Bundle();
                bundle.putLong("mem_num", mem_num);
                fragment.setArguments(bundle);
                transaction.replace(R.id.container, fragment);
                transaction.commit();
            }
        });

        ViewGroup layout2 = (ViewGroup) root.findViewById(R.id.logout);
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        ViewGroup layout3 = (ViewGroup) root.findViewById(R.id.deleteid);
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //다이얼로그 띄워서 "회원탈퇴(Text)" 입력하게 한 후 [확인] 클릭 시 회원탈퇴 진행
                Dialog dialog = new Dialog(getContext());       // Dialog 초기화
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                dialog.setContentView(R.layout.deleteid);
                dialog.show();
                EditText txt = dialog.findViewById(R.id.checkText);
                Button fin = dialog.findViewById(R.id.fin1);
                fin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String text = txt.getText().toString();
                        if(text.compareTo("회원탈퇴") == 0) {

                            RequestQueue requestQueue;
                            Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
                            Network network = new BasicNetwork(new HurlStack());
                            requestQueue = new RequestQueue(cache, network);
                            requestQueue.start();

                            String url = "http://52.78.235.23:8080/personal";

                            Bundle bundle = new Bundle();

                            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url + "/" + mem_num.toString(), new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }
                            });
                            requestQueue.add(stringRequest);

                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            dialog.dismiss(); // 다이얼로그 닫기
                        }
                    }
                });

                Button cancel = dialog.findViewById(R.id.cancel4);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss(); // 다이얼로그 닫기
                    }
                });
            }
        });

        return root;
    }
}