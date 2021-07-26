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

public class DashboardFragment extends Fragment {
    public static final int REQUEST_CODE_MENU = 101;
    //private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
      // dashboardViewModel =
             //   new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        ExerciseAdapter adapter = new ExerciseAdapter();

        adapter.addItem(new  Exercise("3시" ,"4시", "상체"));
        adapter.addItem(new  Exercise("4시" ,"5시", "하체"));
        adapter.addItem(new  Exercise("5시" ,"6시", "상체"));

        recyclerView.setAdapter(adapter);

        Button button = root.findViewById(R.id.groupplan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), ExercisePlan.class);
                startActivityForResult(intent, REQUEST_CODE_MENU);
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




        return root;
    }
}