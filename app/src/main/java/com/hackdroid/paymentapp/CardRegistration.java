package com.hackdroid.paymentapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hackdroid.paymentapp.app.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CardRegistration extends AppCompatActivity {
    String CurrentMobile;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_registration);
        CurrentMobile = Constant.currentRegisterMobile;
        progressBar = (ProgressBar)findViewById(R.id.progress_circular) ;
        Toast.makeText(getApplicationContext() , ""+CurrentMobile , Toast.LENGTH_SHORT).show();
        if(CurrentMobile !=null){
            listene(CurrentMobile);
        }
    }

    private void listene(final String currentMobile) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.Listener, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject result = new JSONObject(response);
                    Boolean error = result.getBoolean("error");
                    if(error==true){
                        listene(currentMobile);
                    }else if(error == false ){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext() , "Card Registration Success" , Toast.LENGTH_SHORT).show();
                        Intent home  = new Intent(getApplicationContext() , SuccessRegistration.class);
                        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(home);
                        finish();
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String , String> map = new HashMap<>();
                map.put("mobile" , currentMobile);
             return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
