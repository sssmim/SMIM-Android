package org.techtown.smim.ui.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.techtown.smim.MainActivity;
import org.techtown.smim.R;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity_getExtra extends AppCompatActivity {

    private EditText getInterest;
    private EditText getImage;
    private Button finish;

    Dialog dialog;

    String Id;
    String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_getextra);

        Intent intent1 = getIntent();
        Id = intent1.getStringExtra("ID");
        pwd = intent1.getStringExtra("PWD");

        getInterest = findViewById(R.id.getINTEREST);
        getImage = findViewById(R.id.getIMAGE);
        finish = findViewById(R.id.finish);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String interest = getInterest.getText().toString();

                String url = "http://52.78.235.23:8080/personal";
                Map map = new HashMap();
                map.put("id", Id);
                map.put("pwd", pwd);
                map.put("interest", interest);
                //map.put("personal_image", );
                JSONObject params = new JSONObject(map);

                JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject obj) {
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }) {

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=UTF-8";
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(objectRequest);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("ID" , Id);
                startActivity(intent);
            }
        });
    }
}
