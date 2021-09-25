package org.techtown.smim.ui.MyPage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.techtown.smim.R;
import org.techtown.smim.ui.dashboard.ExercisePlanFragment;


public class ChangePwd extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.changepwd, container, false);

        Bundle bundle = getArguments();
        Long mem_num = bundle.getLong("mem_num");


        return root;
    }
}