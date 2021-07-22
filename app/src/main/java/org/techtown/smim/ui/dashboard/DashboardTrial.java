package org.techtown.smim.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.smim.R;

public class DashboardTrial extends AppCompatActivity {
    public static final int REQUEST_CODE_MENU = 101;
    private DashboardViewModel dashboardViewModel;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_dashboard);

          //  dashboardViewModel =
      //          new ViewModelProvider(this).get(DashboardViewModel.class);
        //View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        RecyclerView recyclerView =findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        ExerciseAdapter adapter = new ExerciseAdapter();

        adapter.addItem(new  Exercise("3시" ,"4시", "상체"));
        adapter.addItem(new  Exercise("4시" ,"5시", "하체"));
        adapter.addItem(new  Exercise("5시" ,"6시", "상체"));

        recyclerView.setAdapter(adapter);

        Button button = findViewById(R.id.groupplan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExercisePlan.class);
                startActivityForResult(intent, REQUEST_CODE_MENU);
            }
        });

        //return root;
    }
}