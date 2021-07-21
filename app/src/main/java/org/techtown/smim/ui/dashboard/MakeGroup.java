package org.techtown.smim.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.techtown.smim.R;

public class MakeGroup extends AppCompatActivity {
    public static final int num = 922;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_make);

    //private Button button;
    Button button =findViewById(R.id.confirm);
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), FindGroup.class);
            startActivityForResult(intent,num);
        }
    });

}
}
