package org.techtown.smim.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.Nullable;
import org.techtown.smim.R;
import org.techtown.smim.database.group;
import org.techtown.smim.ui.dashboard.GroupList;
import org.techtown.smim.ui.dashboard.GroupListAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomExerciseMerge extends AppCompatActivity {

    public static final int number22 = 13246;
    public static int num0=0;
    public static int num1=0;
    public static int num2=0;
    public static int num3=0;
    public static int num4=0;
    RecyclerView rv;
    static CustomExerciseChoiceAdapter cadapter;
    ItemTouchHelper helper;
    public static List<CustomExerciseChoice> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_exercise_merge);

        rv = findViewById(R.id.rv);
        //RecyclerView의 레이아웃 방식을 지정
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);

        //RecyclerView의 Adapter 세팅
        cadapter = new CustomExerciseChoiceAdapter();
        rv.setAdapter(cadapter);

        //ItemTouchHelper 생성
        helper = new ItemTouchHelper(new ItemTouchHelperCallback(cadapter));
        //RecyclerView에 ItemTouchHelper 붙이기
        helper.attachToRecyclerView(rv);

        CustomExerciseChoice ex1 = new CustomExerciseChoice(R.drawable.push_up,"푸시업","가슴",0);
       CustomExerciseChoice ex2 = new CustomExerciseChoice(R.drawable.pull_up,"풀업","등",0);
        CustomExerciseChoice ex3 = new CustomExerciseChoice(R.drawable.plank,"플랭크","복부",0);
        CustomExerciseChoice ex4 = new CustomExerciseChoice(R.drawable.dumbbell_shoulder_press,"덤벨 숄더 프레스","어깨",0);
       CustomExerciseChoice ex5 = new CustomExerciseChoice(R.drawable.dumbbell_lateral_raise,"덤벨 레터럴 레이즈","어깨",0);
       //// CustomExerciseChoice ex6 = new CustomExerciseChoice(R.drawable.dumbbell_bentover_lateral_raise, "벤트오버 덤벨 레터럴 레이즈","어깨");
       // CustomExerciseChoice ex7 = new CustomExerciseChoice(R.drawable.dumbbell_curl, "덤벨 컬","팔(이두)");
       // CustomExerciseChoice ex8 = new CustomExerciseChoice(R.drawable.triceps_dumbbell_extension, "덤벨 삼두 익스텐션","팔(삼두)");
        //CustomExerciseChoice ex9 = new CustomExerciseChoice(R.drawable.squat, "에어 스쿼트","하체");
        //CustomExerciseChoice ex10 = new CustomExerciseChoice(R.drawable.dumbbell_lunge, "덤벨 런지","하체");

        cadapter.addItem(ex1);
        cadapter.addItem(ex2);

        cadapter.addItem(ex3);
        cadapter.addItem(ex4);
        cadapter.addItem(ex5);
      /*  cadapter.addItem(ex6);
        cadapter.addItem(ex7);
        cadapter.addItem(ex8);
        cadapter.addItem(ex9);
        cadapter.addItem(ex10);*/


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Timer.class);
                startActivityForResult(intent, number22);
            }
        });
    }

    public static void addmethod(CustomExerciseChoice x,  int position){
        //Toast.makeText(get, "value", Toast.LENGTH_LONG).show();
        //Log.e("dd","Dd");
        if (position ==0){
            num0++;
            x.count = num0;
            cadapter.setItem(position,x);
            cadapter.notifyDataSetChanged();}
        if (position ==1){
            num1++;
            x.count = num1;
            cadapter.setItem(position,x);
            cadapter.notifyDataSetChanged();}
        if (position ==2){
            num2++;
            x.count = num2;
            cadapter.setItem(position,x);
            cadapter.notifyDataSetChanged();}
        if (position ==3){
            num3++;
            x.count = num3;
            cadapter.setItem(position,x);
            cadapter.notifyDataSetChanged();}
        if (position ==4){
            num4++;
            x.count = num4;
            cadapter.setItem(position,x);
            cadapter.notifyDataSetChanged();}
    }

    public static void minusmethod(CustomExerciseChoice x,  int position){
        //Toast.makeText(get, "value", Toast.LENGTH_LONG).show();
        //Log.e("dd","Dd");
        if (position ==0){
            num0--;
            x.count = num0;
            cadapter.setItem(position,x);
            cadapter.notifyDataSetChanged();}
        if (position ==1){
            num1--;
            x.count = num1;
            cadapter.setItem(position,x);
            cadapter.notifyDataSetChanged();}
        if (position ==2){
            num2--;
            x.count = num2;
            cadapter.setItem(position,x);
            cadapter.notifyDataSetChanged();}
        if (position ==3){
            num3--;
            x.count = num3;
            cadapter.setItem(position,x);
            cadapter.notifyDataSetChanged();}
        if (position ==4){
            num4--;
            x.count = num4;
            cadapter.setItem(position,x);
            cadapter.notifyDataSetChanged();}
    }
}
