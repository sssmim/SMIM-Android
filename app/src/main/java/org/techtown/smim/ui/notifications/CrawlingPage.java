package org.techtown.smim.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
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

import org.techtown.smim.R;
import org.techtown.smim.ui.dashboard.DashboardFragment;
import org.techtown.smim.ui.dashboard.DashboardTrial;
import org.techtown.smim.ui.dashboard.DashboardViewModel;
import org.techtown.smim.ui.dashboard.MakeGroup;

public class CrawlingPage extends Fragment {

    public static final int num3 = 1326;

    private NotificationsViewModel NotificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = (View)inflater.inflate(R.layout.individual_page, container, false);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.RecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        org.techtown.smim.ui.notifications.PageIndividualListAdapter adapter = new org.techtown.smim.ui.notifications.PageIndividualListAdapter();

        adapter.addItem(new org.techtown.smim.ui.notifications.PageIndividualList("아침 물마시기의 중요성", "물마시기는 ...더보기"));
        adapter.addItem(new org.techtown.smim.ui.notifications.PageIndividualList("미라클 모닝의 효능", "미라클모닝은 ...더보기"));

        recyclerView.setAdapter(adapter);

        //private button button;
        Button button =(Button)root.findViewById(R.id.go_exer);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                NotificationsFragment_test fragment2 = new NotificationsFragment_test();
                transaction.replace(R.id.container, fragment2);
                transaction.commit();
            }
        });

        return root;
    }
}
