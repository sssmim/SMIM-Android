package org.techtown.smim.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.smim.R;

public class YoutubePlan extends AppCompatActivity {
    public static final int youtubetoplan = 102;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_plan);

        Button button = findViewById(R.id.youtubeadd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ExercisePlan.class);
                startActivityForResult(intent,youtubetoplan);
                finish();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView1);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        YoutubeAdapter adapter = new YoutubeAdapter();

        adapter.addItem(new Youtube("상체"));


        recyclerView.setAdapter(adapter);


    }
}