package org.techtown.smim.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.techtown.smim.R;

import java.util.HashMap;
import java.util.Map;


public class makegroup1 extends Fragment {

    public TextView groupname;
    public TextView groupdesc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_makegroup1, container, false);

        groupname = root.findViewById(R.id.groupname2);
        groupdesc = root.findViewById(R.id.groupdesc);
        //private Button button;
        Button button =root.findViewById(R.id.confirm);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://52.78.235.23:8080/organization";
                Map map = new HashMap();
                map.put("group_name", groupname.getText().toString());
                map.put("group_desc", groupdesc.getText().toString());
                map.put("group_category", "and test33");
                map.put("view_count", 22);
                map.put("authority", 1);
                JSONObject params = new JSONObject(map);

                JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject obj) {
                                Log.e("dd", "ss");
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
                objectRequest.setTag("post");
                RequestQueue queue = Volley.newRequestQueue(requireContext());
                queue.add(objectRequest);
                //finish();

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                DashboardFragment fragment2 = new DashboardFragment();
                transaction.replace(R.id.container, fragment2);
                transaction.commit();
                //Intent intent = new Intent(requireContext(), DashboardTrial.class);
                //startActivityForResult(intent, 101);


            }

        });




        return root;
    }
}