package org.techtown.smim.ui.dashboard;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import org.techtown.smim.database.group;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    public static final int REQUEST_CODE_MENU = 101;
    //private DashboardViewModel dashboardViewModel;
    public TextView name;
    public TextView info;

    public List<group> list = new ArrayList<>();
    public List<gexercise> list2 = new ArrayList<>();
    Integer po;
    Long Group_num;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
      // dashboardViewModel =
             //   new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        name = root.findViewById(R.id.name);
        info = root.findViewById(R.id.info);

        Bundle bundle = getArguments();
        //if(bundle!=null){
        po =  bundle.getInt("Obj");
        Group_num = bundle.getLong("Group_num");
    //}
        //System.out.println(a);

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String url = "http://52.78.235.23:8080/organization";

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
                name.setText(list.get(po).group_name);
                info.setText(list.get(po).group_desc);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);

        RequestQueue requestQueue1;
        Cache cache1 = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network1 = new BasicNetwork(new HurlStack());
        requestQueue1 = new RequestQueue(cache, network);
        requestQueue1.start();

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);


        ExerciseAdapter adapter = new ExerciseAdapter();

        String url2 = "http://52.78.235.23:8080/gexercise";

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                // 한글깨짐 해결 코드
                String changeString2 = new String();
                try {
                    changeString2 = new String(response.getBytes("8859_1"),"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Type listType = new TypeToken<ArrayList<gexercise>>(){}.getType();
                list2 = gson.fromJson(changeString2, listType);

                for(int i = 0; i< list2.size(); i++) {
                    if(list2.get(i).group_num == Group_num){
                        adapter.addItem(new Exercise(list2.get(i).ge_start_time, list2.get(i).ge_end_time, list2.get(i).ge_desc));
                    }
                }

                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(stringRequest2);

        Button button = root.findViewById(R.id.groupplan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), ExercisePlan.class);
                startActivityForResult(intent, REQUEST_CODE_MENU);
                //FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                //ExercisePlanFragment fragment2 = new ExercisePlanFragment();
                //transaction.replace(R.id.container, fragment2);
                //transaction.commit();

            }
        });

        Button button1 = root.findViewById(R.id.groupplay);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), GroupExercisePlay.class);
                startActivityForResult(intent, REQUEST_CODE_MENU);
            }
        });

        Button refresh_button = root.findViewById(R.id.refresh);
        refresh_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return root;
    }
}