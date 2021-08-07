package org.techtown.smim.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.smim.R;

public class CustomExerciseMerge extends AppCompatActivity {

    public static final int number22 = 13246;
    RecyclerView rv;
    CustomExerciseChoiceAdapter cadapter;
    ItemTouchHelper helper;

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

        CustomExerciseChoice ex1 = new CustomExerciseChoice(R.drawable.push_up,"푸시업","가슴");
        CustomExerciseChoice ex2 = new CustomExerciseChoice(R.drawable.pull_up,"풀업","등");
        CustomExerciseChoice ex3 = new CustomExerciseChoice(R.drawable.plank,"플랭크","복부");
        CustomExerciseChoice ex4 = new CustomExerciseChoice(R.drawable.dumbbell_shoulder_press,"덤벨 숄더 프레스","어깨");
        CustomExerciseChoice ex5 = new CustomExerciseChoice(R.drawable.dumbbell_lateral_raise,"덤벨 레터럴 레이즈","어깨");
        CustomExerciseChoice ex6 = new CustomExerciseChoice(R.drawable.dumbbell_bentover_lateral_raise, "벤트오버 덤벨 레터럴 레이즈","어깨");
        CustomExerciseChoice ex7 = new CustomExerciseChoice(R.drawable.dumbbell_curl, "덤벨 컬","팔(이두)");
        CustomExerciseChoice ex8 = new CustomExerciseChoice(R.drawable.triceps_dumbbell_extension, "덤벨 삼두 익스텐션","팔(삼두)");
        CustomExerciseChoice ex9 = new CustomExerciseChoice(R.drawable.squat, "에어 스쿼트","하체");
        CustomExerciseChoice ex10 = new CustomExerciseChoice(R.drawable.dumbbell_lunge, "덤벨 런지","하체");

        cadapter.addItem(ex1);
        cadapter.addItem(ex2);
        cadapter.addItem(ex3);
        cadapter.addItem(ex4);
        cadapter.addItem(ex5);
        cadapter.addItem(ex6);
        cadapter.addItem(ex7);
        cadapter.addItem(ex8);
        cadapter.addItem(ex9);
        cadapter.addItem(ex10);


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExerciseTimer.class);
                startActivityForResult(intent, number22);
            }
        });
    }
}
