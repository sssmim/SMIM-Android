package org.techtown.smim.ui.notifications;

import android.content.Intent;
import android.net.Uri;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.techtown.smim.R;
import org.techtown.smim.database.personal;
import org.techtown.smim.ui.dashboard.DashboardFragment;
import org.techtown.smim.ui.dashboard.DashboardTrial;
import org.techtown.smim.ui.dashboard.DashboardViewModel;
import org.techtown.smim.ui.dashboard.ExerciseAdapter;
import org.techtown.smim.ui.dashboard.MakeGroup;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CrawlingPage extends Fragment {

    public static final int num3 = 1326;
    public static int equalic = 0;
    public static String s2= null;

    public List<personal> list3 = new ArrayList<>();

    private NotificationsViewModel NotificationsViewModel;

    Long mem_num;

    org.techtown.smim.ui.notifications.PageIndividualListAdapter adapter = new org.techtown.smim.ui.notifications.PageIndividualListAdapter();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = (View) inflater.inflate(R.layout.individual_page, container, false);
        regionData task = new regionData();
        task.execute();

        adapter.setOnItemClicklistener(new PageIndividualListAdapter.OnNewsItemClickListener() {
            @Override
            public void onItemClick(PageIndividualListAdapter.ViewHolder holder, View view, int position) {
                //Toast.makeText(getContext(),"아이템 선택 "+position, Toast.LENGTH_LONG).show();
                Log.d("test_link", adapter.getItem(position).link);
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.hidoc.co.kr" + adapter.getItem(position).link));
                startActivity(myIntent);
            }
        });

        Bundle bundle = getArguments();
        mem_num = bundle.getLong("mem_num");
        Log.d("test_CrawlingPage", String.valueOf(mem_num));

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
                String tag_raw = null;
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

                    tag_raw = doc.get(i).select("li.meta_item").text();
                    String[] array2 = tag_raw.split(",");


                    writer = doc.get(i).select("li.txt_expert").text();
                    //Log.d("받아와지는지 확인",title);
                    //System.out.println("/d");





                            //String s2 = new String("수면장애");

                    for (int j = 0; j < array2.length; j++) {
                        Log.d("어레이 태그 테스트", array2[j]);
                        if (s2.equals(array2[j]) == true) {
                            arrayList.add(new ListData(title, image, tothelink, tag, writer));
                            //Log.d("리스트저장되어지는지 확인",arrayList.get(i).getTv_cases());
                        }
                    }


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
            DividerItemDecoration dividerItemDecoration =
                    new DividerItemDecoration(getActivity().getApplicationContext(), new LinearLayoutManager(getContext()).getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.setLayoutManager(layoutManager);

            adapter.clearItem();

            for (int i = 0; i <arrayList.size(); i++) {
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

