package com.hackdroid.paymentapp.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hackdroid.paymentapp.ForgetPin;
import com.hackdroid.paymentapp.HomeUser;
import com.hackdroid.paymentapp.R;
import com.hackdroid.paymentapp.RegisterAccount;

public class Router extends AppCompatActivity {
Button register , forgetPsd ,home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_router);
        register = (Button)findViewById(R.id.register);
        forgetPsd = (Button)findViewById(R.id.forgetPsd);
        home  = (Button)findViewById(R.id.home) ;
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regitser = new Intent(getApplicationContext() , RegisterAccount.class);
                regitser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(regitser);
                overridePendingTransition(android.support.design.R.anim.abc_fade_in, android.support.design.R.anim.abc_fade_out);
            }
        });
        forgetPsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgetActivity = new Intent(getApplicationContext() , ForgetPin.class);
                forgetActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(forgetActivity);
                overridePendingTransition(android.support.design.R.anim.abc_fade_in , android.support.design.R.anim.abc_fade_out);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getApplicationContext() , HomeUser.class);
                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(home);
                overridePendingTransition(android.support.design.R.anim.abc_fade_in, android.support.design.R.anim.abc_fade_out);
            }
        });
    }
}
