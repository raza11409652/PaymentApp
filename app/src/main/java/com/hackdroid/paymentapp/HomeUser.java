package com.hackdroid.paymentapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.hackdroid.paymentapp.app.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeUser extends AppCompatActivity {
    TextView name , phone ,card ,time;
    Button r;
    CircleImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);
        name = (TextView)findViewById(R.id.name);
        phone = (TextView)findViewById(R.id.phone);
        card = (TextView)findViewById(R.id.card);
        time = (TextView)findViewById(R.id.time);
        r= (Button)findViewById(R.id.reload);
        imageView = (CircleImageView) findViewById(R.id.image);
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchProfile();
            }
        });
        fetchProfile();
    }

    private void fetchProfile() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.ListenerCard, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext() , ""+response , Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String namev = jsonObject.getString("name");
                    String phonev = jsonObject.getString("mobile");
                    String cardv = jsonObject.getString("card");
                    String image = jsonObject.getString("image");
                    String date = jsonObject.getString("timeval") ;
                    name.setText("NAME : "+namev);
                    phone.setText("REG NO : "+phonev);
                    card.setText("CARD NO : "+cardv);
                    time.setText("LAST ATTENDANCE  : "+date);
                    Glide.with(getApplicationContext()).load(Constant.ROOT_HOST+image).into(imageView);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
