package org.techtown.smim.ui.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.techtown.smim.R;

public class LoginActivity_setPwd extends AppCompatActivity {

    private EditText getPwd1;
    private EditText getPwd2;
    private Button checkPwd;

    private boolean check = false;

    Dialog dialog;

    String Id;
    String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_set);

        Intent intent1 = getIntent();
        Id = intent1.getStringExtra("ID");

        getPwd1 = findViewById(R.id.pwd1);
        getPwd2= findViewById(R.id.pwd2);
        checkPwd = findViewById(R.id.finish);

        checkPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Pwd1 = getPwd1.getText().toString();
                String Pwd2 = getPwd2.getText().toString();

                if(Pwd1.compareTo(Pwd2) != 0) {
                    dialog = new Dialog(LoginActivity_setPwd.this);       // Dialog 초기화
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                    dialog.setContentView(R.layout.login_setpwd_dialog);
                    dialog.show();
                    Button cancel = dialog.findViewById(R.id.btnCancel3);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss(); // 다이얼로그 닫기
                        }
                    });
                } else {
                    pwd = Pwd1;
                    Intent intent = new Intent(getApplicationContext(), LoginActivity_getExtra.class);
                    intent.putExtra("ID" , Id);
                    intent.putExtra("PWD" , pwd);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
