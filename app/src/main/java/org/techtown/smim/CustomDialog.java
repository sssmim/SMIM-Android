package org.techtown.smim;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

public class CustomDialog extends Dialog {


    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);

        // 다이얼로그의 배경을 투명으로 만든다.
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.

        Button saveButton = findViewById(R.id.btnres);
        Button cancelButton = findViewById(R.id.btndel);

        // 버튼 리스너 설정
        saveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(mContext,"j", Toast.LENGTH_SHORT).show();
                // Custom Dialog 종료
                dismiss();
            }
        });
        cancelButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // '취소' 버튼 클릭시
                // ...코드..
                // Custom Dialog 종료
                dismiss();
            }
        });

    }

    public CustomDialog(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }


}