package org.techtown.smim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.techtown.smim.ui.LoginActivity;
import org.techtown.smim.ui.dashboard.FindGroup_test;
import org.techtown.smim.ui.home.HomeFragment;
import org.techtown.smim.ui.notifications.CrawlingPage;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private LoginActivity loginActivity = new LoginActivity();
    private HomeFragment homeFragment = new HomeFragment();
    //private FindGroup findGroup = new FindGroup();
    private FindGroup_test findGroup_test = new FindGroup_test();
    private CrawlingPage crawlingPage = new CrawlingPage();

    public String mem_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent1 = getIntent();
        mem_num = intent1.getStringExtra("mem_num");

        Bundle bundle = new Bundle();
        bundle.putString("mem_num", mem_num);

        homeFragment.setArguments(bundle);
        findGroup_test.setArguments(bundle);
        crawlingPage.setArguments(bundle);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, homeFragment).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(menuItem.getItemId())
            {
                case R.id.navigation_home:
                    transaction.replace(R.id.container, homeFragment).commitAllowingStateLoss();

                    break;
                case R.id.navigation_dashboard:
                    transaction.replace(R.id.container, findGroup_test).commitAllowingStateLoss();
                    break;
                case R.id.navigation_notifications:
                    transaction.replace(R.id.container,crawlingPage).commitAllowingStateLoss();
                    break;
            }
            return true;
        }

    }
}