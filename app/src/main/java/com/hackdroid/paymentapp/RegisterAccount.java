package com.hackdroid.paymentapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class RegisterAccount extends AppCompatActivity {
    EditText nameInput , mobileInput , pinInput;
    String name , mobile , pin;
    Button register;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);
        setTitle("Register Your Account");
        //init
        nameInput = (EditText)findViewById(R.id.name);
       mobileInput= (EditText)findViewById(R.id.mobile);
       pinInput = (EditText)findViewById(R.id.pin);
       register = (Button)findViewById(R.id.register);
       progressDialog = new ProgressDialog(this);
       progressDialog.setCanceledOnTouchOutside(false);
       progressDialog.setMessage("Please wait while profile is being created ");
       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               name = nameInput.getText().toString().trim();
               mobile = mobileInput.getText().toString().trim();
               pin = pinInput.getText().toString();
               if (TextUtils.isEmpty(name)){
                   nameInput.setError("Required");
                   return;
               }else if(TextUtils.isEmpty(mobile)){
                   mobileInput.setError("Required");
                   return;
               }else if(TextUtils.isEmpty(pin)){
                   pinInput.setError("Required");
                   return;
               }else{
                   progressDialog.show();
                   startRegistration(name , mobile , pin);
               }
           }
       });
    }

    private void startRegistration(final String name, final String mobile, final String pin) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
               // Toast.makeText(getApplicationContext() , response , Toast.LENGTH_SHORT).show();
                try {
                    JSONObject result = new JSONObject(response);
                    Boolean error = result.getBoolean("error");
                    String msg = result.getString("msg");
                    if(error == true){
                        Toast.makeText(getApplicationContext() , ""+msg  , Toast.LENGTH_SHORT).show();
                    }else if(error == false){
                        Constant.currentRegisterMobile = mobile;
                        Intent uploadImage = new Intent(getApplicationContext() , UplaodImage.class);
                        uploadImage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(uploadImage);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext() , "Connection Error with server" , Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String , String> map = new HashMap<>();
                map.put("name" , name);
                map.put("mobile" , mobile);
                map.put("pin" , pin);
                return  map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
