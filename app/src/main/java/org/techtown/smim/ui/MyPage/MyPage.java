package org.techtown.smim.ui.MyPage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.techtown.smim.MainActivity;
import org.techtown.smim.R;
import org.techtown.smim.ui.dashboard.ExercisePlanFragment;
import org.techtown.smim.ui.login.LoginActivity;


public class MyPage extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_my_page, container, false);

        Bundle bundle = getArguments();
        Long mem_num = bundle.getLong("mem_num");

        ViewGroup layout1 = (ViewGroup) root.findViewById(R.id.changePwd);
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                ChangePwd fragment = new ChangePwd();
                Bundle bundle = new Bundle();
                bundle.putLong("mem_num", mem_num);
                fragment.setArguments(bundle);
                transaction.replace(R.id.container, fragment);
                transaction.commit();
            }
        });

        ViewGroup layout2 = (ViewGroup) root.findViewById(R.id.logout);
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        ViewGroup layout3 = (ViewGroup) root.findViewById(R.id.deleteid);
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //다이얼로그 띄워서 "회원탈퇴(Text)" 입력하게 한 후 [확인] 클릭 시 회원탈퇴 진행
            }
        });
        return root;
    }
}