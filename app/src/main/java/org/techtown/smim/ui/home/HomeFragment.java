package org.techtown.smim.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.smim.R;
import org.techtown.smim.ui.dashboard.ExercisePlan;
import org.techtown.smim.ui.dashboard.FindGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    String mem_num;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Bundle bundle = getArguments();
        mem_num = bundle.getString("mem_num");
        Log.d("test_HomeFragment", mem_num);

        TextView textView01 = (TextView) root.findViewById(R.id.textView1);

        long Now = System.currentTimeMillis();
        Date mDate = new Date(Now);

        SimpleDateFormat simpleYear = new SimpleDateFormat("yyyy.M");
        String getYear = simpleYear.format(mDate);
        SimpleDateFormat simpleDay = new SimpleDateFormat("dd (EE)", Locale.getDefault());
        String getDay = simpleDay.format(Now);
        textView01.setText(getYear);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.RecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        org.techtown.smim.ui.home.ActivityListAdapter adapter = new org.techtown.smim.ui.home.ActivityListAdapter();

        adapter.addItem(new org.techtown.smim.ui.home.ActivityList("스쿼트", "10:00 ~ 12:00"));
        adapter.addItem(new org.techtown.smim.ui.home.ActivityList("런지", "14:00 ~ 17:00"));
        adapter.addItem(new org.techtown.smim.ui.home.ActivityList("무야호", "15:00 ~ 19:00"));

        recyclerView.setAdapter(adapter);

        CalendarView calendar = (CalendarView) root.findViewById(R.id.calendarView);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String year_m = year + "." + (month + 1);
                textView01.setText(year_m);
                String day = dayOfMonth + "";
            }
        });
        Button test = (Button) root.findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), test.class);
                startActivity(intent);
            }
        });
        return root;
    }
}