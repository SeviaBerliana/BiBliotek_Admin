package com.bibliotek.ui.LoginAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bibliotek.MainActivity;
import com.bibliotek.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button login;
    FirebaseAuth firebaseAuth;
    private SharedPreferences preferences;
    public static final int mode = Activity.MODE_PRIVATE;
    private String emailTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initiate firebaseauthentication
        firebaseAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.username_login);
        password = findViewById(R.id.password_login);
        login = findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email.getText().toString().equals("") && !password.getText().toString().equals("")) {
                    //login
                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                loadPreferences();
                                savePreferences(email.getText().toString().trim());
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            }
                            else if(password.getText().toString().length()<6)
                                Toast.makeText(LoginActivity.this, "Password kurang dari 6 karakter!", Toast.LENGTH_SHORT).show();
                            else if (!(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()))
                                Toast.makeText(LoginActivity.this, "Salah Format Email!", Toast.LENGTH_SHORT).show();
                            else {
                                Toast.makeText(LoginActivity.this, "Email / password wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    if(email.getText().toString().equals("") && password.getText().toString().equals(""))
                        Toast.makeText(LoginActivity.this, "Email dan Password masih kosong!", Toast.LENGTH_SHORT).show();
                    else if(email.getText().toString().equals(""))
                        Toast.makeText(LoginActivity.this, "Email masih kosong!", Toast.LENGTH_SHORT).show();
                    else if(password.getText().toString().equals(""))
                        Toast.makeText(LoginActivity.this, "Password masih kosong!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText( LoginActivity.this,"Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadPreferences() {
        String name = "profile";
        preferences = getSharedPreferences(name, mode);
        if (preferences != null) {
            emailTemp = preferences.getString("email", "");
        }
    }

    private void savePreferences(String email1) {
        SharedPreferences.Editor editor = preferences.edit();
        if (!email1.isEmpty()) {
            editor.putString("email", email1);
            editor.apply();
            Toast.makeText(this, "Email saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Fill correctly", Toast.LENGTH_SHORT).show();
        }
    }
}