package org.techtown.smim.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.techtown.smim.R;

public class FindGroup extends Fragment {
    public static final int num1 = 326;
    private DashboardViewModel dashboardViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = (View)inflater.inflate(R.layout.group_find, container, false);

        //private button button;
        FloatingActionButton button =(FloatingActionButton)root.findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), MakeGroup.class);
                startActivityForResult(intent, num1);
            }
        });
        return root;
    }
}
