package org.techtown.smim.ui.notifications;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
import org.jsoup.nodes.Element;
import org.techtown.smim.R;
import org.techtown.smim.ui.dashboard.DashboardFragment;
import org.techtown.smim.ui.dashboard.DashboardTrial;
import org.techtown.smim.ui.dashboard.DashboardViewModel;
import org.techtown.smim.ui.dashboard.MakeGroup;

import java.net.URLEncoder;
import java.util.ArrayList;

public class CrawlingPage extends Fragment {

    public static final int num3 = 1326;

    private NotificationsViewModel NotificationsViewModel;

    Long mem_num;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = (View) inflater.inflate(R.layout.individual_page, container, false);
        regionData task = new regionData();
        task.execute();

        Bundle bundle = getArguments();
        mem_num = bundle.getLong("mem_num");
        Log.d("test_CrawlingPage", String.valueOf(mem_num));

        //  RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.RecyclerView);

        // LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        //recyclerView.setLayoutManager(layoutManager);

        //org.techtown.smim.ui.notifications.PageIndividualListAdapter adapter = new org.techtown.smim.ui.notifications.PageIndividualListAdapter();

        //adapter.addItem(new org.techtown.smim.ui.notifications.PageIndividualList("아침 물마시기의 중요성", "물마시기는 ...더보기"));
        //adapter.addItem(new org.techtown.smim.ui.notifications.PageIndividualList("미라클 모닝의 효능", "미라클모닝은 ...더보기"));

        //recyclerView.setAdapter(adapter);

        //private button button;
        Button button = (Button) root.findViewById(R.id.go_exer);
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
                // String str = "필라테스";
                // String utf8= URLEncoder.encode(str,"UTF-8");//쿼리문에들어갈 한글인코딩
                // String url = "https://brunch.co.kr/search?q="+utf8;

                /* Jsoup을 이용해 데이터 가져오기 */
                Document document = Jsoup.connect("https://www.hidoc.co.kr/healthstory/news?organ=0&mIdx=1020&gender=0&season=0&page=3&life=0&sIdx=1120&care=0").get();
                Elements doc = document.select("#hidocBody > div.cont_news > ul > li");
                String strdoc = doc.text();
                //System.out.print(document); //url은 받아와짐!
                //System.out.print(strdoc); //doc도 받아와짐
                int region_num = 0;
                String title_raw = null;
                String title = null;
                String image = null;
                String tothelink = null;
                String tag = null;
                String writer = null;

                for (int i = 0; i < doc.size(); i++) {
                    title_raw = doc.get(i).select("div.news_info a[title]").get(0).text(); //제목

                    String[] array = title_raw.split("]");
                    title = array[1];

                    //image = doc.get(i).select("a img[src]").text();
                    image = doc.get(i).getElementsByAttribute("src").attr("src");

                    //tothelink = doc.get(i).select(" a[href]").text();
                    tothelink = doc.get(i).getElementsByAttribute("href").attr("href");

                    tag = doc.get(i).select("li.meta_item").text();
                    writer = doc.get(i).select("li.txt_expert").text();
                    //Log.d("받아와지는지 확인",title);
                    //System.out.println("/d");


                    arrayList.add(new ListData(title, image, tothelink, tag, writer));
                    //Log.d("리스트저장되어지는지 확인",arrayList.get(i).getTv_cases());
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<ListData> arrayList) {
            RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.RecyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            org.techtown.smim.ui.notifications.PageIndividualListAdapter adapter = new org.techtown.smim.ui.notifications.PageIndividualListAdapter();

            for (int i = 0; i < 3; i++) {
                String title = arrayList.get(i).getTv_name();
                String writer = arrayList.get(i).getTv_recovered();
                String img = arrayList.get(i).getTv_cases();
                String link = arrayList.get(i).getTv_cases_p();
                //Log.d("이미지 좀 확인 할게요",img);


                adapter.addItem(new org.techtown.smim.ui.notifications.PageIndividualList(img, title, writer, link));

            }
            recyclerView.setAdapter(adapter);


        }

    }
}

/*링크 추출문

Elements linkElements = document.select("a.course_card_front");

            for (int j = 0; j < linkElements.size(); j++) {
                final String url = linkElements.get(j).attr("abs:href");
            }

이미지 추출문

 try {
            Document document = conn.get();
            Elements imageUrlElements = document.getElementsByClass("swiper-lazy");

            for (Element element : imageUrlElements) {
                System.out.println(element);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
\

  for (Element element : imageUrlElements) {
                System.out.println(element.attr("abs:src");
            }


*/

