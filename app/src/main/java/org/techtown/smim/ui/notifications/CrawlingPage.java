package org.techtown.smim.ui.notifications;

import android.content.Intent;
import android.os.AsyncTask;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.techtown.smim.R;
import org.techtown.smim.ui.dashboard.DashboardFragment;
import org.techtown.smim.ui.dashboard.DashboardTrial;
import org.techtown.smim.ui.dashboard.DashboardViewModel;
import org.techtown.smim.ui.dashboard.MakeGroup;

import java.util.ArrayList;

public class CrawlingPage extends Fragment {

    public static final int num3 = 1326;

    private NotificationsViewModel NotificationsViewModel;

    Long mem_num;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = (View)inflater.inflate(R.layout.individual_page, container, false);
        regionData task = new regionData();
        task.execute();

        Bundle bundle = getArguments();
        mem_num = bundle.getLong("mem_num");
        Log.d("test_CrawlingPage", String.valueOf(mem_num));

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.RecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        org.techtown.smim.ui.notifications.PageIndividualListAdapter adapter = new org.techtown.smim.ui.notifications.PageIndividualListAdapter();

        //adapter.addItem(new org.techtown.smim.ui.notifications.PageIndividualList("아침 물마시기의 중요성", "물마시기는 ...더보기"));
        //adapter.addItem(new org.techtown.smim.ui.notifications.PageIndividualList("미라클 모닝의 효능", "미라클모닝은 ...더보기"));

        recyclerView.setAdapter(adapter);

        //private button button;
        Button button =(Button)root.findViewById(R.id.go_exer);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                NotificationsFragment_test fragment2 = new NotificationsFragment_test();
                Bundle bundle = new Bundle();
                bundle.putLong("mem_num", mem_num);
                fragment2.setArguments(bundle);
                transaction.replace(R.id.container, fragment2);
                transaction.commit();
            }
        });

        return root;
    }
    private class regionData extends AsyncTask<Void, Void, ArrayList<ListData>> {

        @Override
        protected ArrayList<ListData> doInBackground(Void... voids) {

            ArrayList<ListData> arrayList = new ArrayList<ListData>();

            try {
                /* Jsoup을 이용해 데이터 가져오기 */
                Document document = Jsoup.connect("http://ncov.mohw.go.kr/").get();
                Elements doc = document.select("div.rpsa_detail > div > div");

                int region_num = 0;
                String region = null;
                String region_cases = null;
                String region_cases_p = null;
                String region_deaths = null;
                String region_recovered = null;

                for(int i=1; i<doc.size(); i++) {

                    region = doc.get(i).select("h4").text();
                    region_cases = doc.get(i).select("li").get(0).select("div").get(1).select("span").text();
                    region_cases_p = doc.get(i).select("li").get(1).select("div").get(1).select("span").text();
                    region_deaths = doc.get(i).select("li").get(2).select("div").get(1).select("span").text();
                    region_recovered = doc.get(i).select("li").get(3).select("div").get(1).select("span").text();

                    if(region_cases_p.equals("(0)")){
                        region_cases_p = "";
                    }

                    arrayList.add(new ListData(region, region_cases, region_cases_p, region_deaths, region_recovered));

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<ListData> arrayList) {
            String str1 = arrayList.get(0).toString();
            String str2 = arrayList.get(1).toString();
            org.techtown.smim.ui.notifications.PageIndividualListAdapter adapter = new org.techtown.smim.ui.notifications.PageIndividualListAdapter();
            adapter.addItem(new org.techtown.smim.ui.notifications.PageIndividualList(str1, str2));
        }
    }

}
