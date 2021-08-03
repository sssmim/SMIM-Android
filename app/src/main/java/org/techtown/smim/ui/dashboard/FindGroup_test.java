package org.techtown.smim.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.smim.R;
import org.techtown.smim.database.group;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindGroup_test extends Fragment {
    public static final int num1 = 326;
    public static final int num2 = 328;

    private DashboardViewModel dashboardViewModel;

    public List<group> list = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = (View)inflater.inflate(R.layout.group_find, container, false);
        Button post = (Button)root.findViewById(R.id.post);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.RecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.bringToFront();
        GroupListAdapter adapter = new GroupListAdapter();

        RequestQueue requestQueue;

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);

        // Start the queue
        requestQueue.start();

        String url = "http://52.78.235.23:8080/organization";

        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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
                Type listType = new TypeToken<ArrayList<group>>(){}.getType();
                list = gson.fromJson(changeString, listType);

                for(int i = 0; i< list.size(); i++) {
                    adapter.addItem(new GroupList(list.get(i).group_name, list.get(i).group_desc));
                }

                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);


        //private button button;
        FloatingActionButton button =(FloatingActionButton)root.findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(requireContext(), MakeGroup.class);
                //startActivityForResult(intent, num1);

                //Intent intent = new Intent(requireContext(), DashboardTrial.class);
                //startActivityForResult(intent, num2);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                makegroup1 fragment2 = new  makegroup1();
                transaction.replace(R.id.container, fragment2);
                transaction.commit();
            }
        });

        Button button2 =(Button)root.findViewById(R.id.btn_move);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(requireContext(), DashboardTrial.class);
                //startActivityForResult(intent, num2);
                //FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                //DashboardFragment fragment2 = new DashboardFragment();
                //transaction.replace(R.id.container, fragment2);
                //transaction.commit();
            }
        });

        return root;
    }
}
