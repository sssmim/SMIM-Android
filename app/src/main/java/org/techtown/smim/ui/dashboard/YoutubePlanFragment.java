package org.techtown.smim.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import org.techtown.smim.database.group;
import org.techtown.smim.database.iexercise;
import org.techtown.smim.database.video;
import org.techtown.smim.ui.notifications.CustomExercise;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class YoutubePlanFragment extends Fragment {
    public static final int youtubetoplan = 102;

    public List<video> list = new ArrayList<>();
    public List<String> url2 = new ArrayList<>();
    public List<String> title2 = new ArrayList<>();
    public String realurl;

    public Long mem_num;
    public Long group_num;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_youtube_plan, container, false);

        Bundle bundle = getArguments();
        if(String.valueOf(bundle.getLong("mem_num")) != null) {
            mem_num = bundle.getLong("mem_num");
        }
        if(String.valueOf(bundle.getLong("group_num")) != null) {
            group_num = bundle.getLong("group_num");
        }

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView1);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        YoutubeAdapter adapter = new YoutubeAdapter();

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String url = "http://52.78.235.23:8080/video";

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
                Type listType = new TypeToken<ArrayList<video>>() {
                }.getType();
                list = gson.fromJson(changeString, listType);

                for (int i = 0; i < list.size(); i++) {
                    int image = getResources().getIdentifier(list.get(i).video_image, "drawable", getActivity().getPackageName());
                    adapter.addItem(new Youtube(list.get(i).video_name, image));
                    url2.add(list.get(i).video_url);
                    title2.add(list.get(i).video_name);
                }

                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(stringRequest);

        //adapter.addItem(new Youtube("상체"));
        //adapter.addItem(new Youtube("하체"));
        //adapter.addItem(new Youtube("복부"));
        //adapter.addItem(new Youtube("스쿼트"));
        //recyclerView.setAdapter(adapter);
        adapter.setOnItemClicklistener(new YoutubeAdapter.OnYoutubeItemClickListener() {
            @Override
            public void onItemClick(YoutubeAdapter.ViewHolder holder, View view, int position) {
                Youtube item = adapter.getItem(position);

                realurl = url2.get(position);
                Toast.makeText(requireContext(), title2.get(position) + "동영상이 선택되었습니다", Toast.LENGTH_LONG).show();
            }
        });

        Button button = root.findViewById(R.id.youtubeadd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                ExercisePlanFragment fragment2 = new ExercisePlanFragment();
                Bundle bundle = new Bundle();
                bundle.putString("realurl", realurl);
                bundle.putLong("mem_num", mem_num);
                bundle.putLong("group_num", group_num);
                fragment2.setArguments(bundle);
                transaction.replace(R.id.container, fragment2);
                transaction.commit();
            }
        });
        return root;
    }
}