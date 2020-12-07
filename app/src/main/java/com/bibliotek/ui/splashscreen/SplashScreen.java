package com.bibliotek.ui.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.bibliotek.MainActivity;
import com.bibliotek.R;
import com.bibliotek.ui.LoginAdmin.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private SharedPreferences preferences;
    public static final int mode = Activity.MODE_PRIVATE;
    private String emailTemp;
    TextView tv_welcome;
    Thread timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        firebaseAuth = FirebaseAuth.getInstance();
        tv_welcome = findViewById(R.id.splash_welcome);
        loadPreferences();
    }

    private void loadPreferences() {
        String name = "profile";
        preferences = getSharedPreferences(name, mode);
        emailTemp = preferences.getString("email", "");
        if ( firebaseAuth.getCurrentUser() != null ) {
            tv_welcome.setText("Hi, " + emailTemp);
            timer = new Thread() {
                @Override
                public void run() {
                    try {
                        synchronized (this) {
                            wait(3000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
                        finish();
                    }
                }
            };
            timer.start();
        } else {
            tv_welcome.setText("");
            timer = new Thread() {
                @Override
                public void run() {
                    try {
                        synchronized (this) {
                            wait(5000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                        finish();
                    }
                }
            };
            timer.start();
        }
    }
}