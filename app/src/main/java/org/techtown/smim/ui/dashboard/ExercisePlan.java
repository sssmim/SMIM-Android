package org.techtown.smim.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.techtown.smim.R;
//eee
public class ExercisePlan extends AppCompatActivity {
    public static final int plantoyoutbe = 102;
    public static final int plantomain = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_plan);



        Button button = findViewById(R.id.chooseyoutube);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), YoutubePlan.class);
                startActivityForResult(intent,plantoyoutbe);
            }
        });
        Button button1 = findViewById(R.id.addplan);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent intent = new Intent(getApplicationContext(),DashboardFragment.class);
//                startActivityForResult(intent,plantomain);
              //  var fragment2 = Fragment2()
              //  var bundle = Bundle()
               // bundle.putInt("num1",1)
                //bundle.putInt("num2",2)
                //fragment2.arguments = bundle //fragment의 arguments에 데이터를 담은 bundle을 넘겨줌

              //  activity?.supportFragmentManager!!.beginTransaction()
               //         .replace(R.id.view_main, fragment2)
               //         .commit()
                finish();
            }
        });
    }
    }
