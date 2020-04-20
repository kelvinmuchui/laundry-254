package com.example.banice.laundry254;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.banice.laundry254.user.The_user_profile;
import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                // This method will be executed once the timer is over
                // Start your app main activity

                if (isNetworkAvailable()) {

                    if(FirebaseAuth.getInstance().getCurrentUser()!=null)
                    {
                        startActivity(new Intent(Splash.this, The_user_profile.class));
                        finish();
                        overridePendingTransition(R.anim.top2,R.anim.top1);
                    }else
                    {
                        Intent i = new Intent(Splash.this, Launcher.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.top2,R.anim.top1);
                    }

                } else {
                    Intent i = new Intent(Splash.this, NoInternet.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fromright,R.anim.toright );

                }


                // close this activity
                finish();
            }
        }, 5000);
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
