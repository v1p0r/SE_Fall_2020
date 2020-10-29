package com.example.healthmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class LoginActivity extends AppCompatActivity {
    EditText accountEditText;
    EditText pwdEditText;
    Button logInBtn;
    Button signInBtn;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        accountEditText = findViewById(R.id.account_input);

        pwdEditText = findViewById(R.id.password_input);
        logInBtn = findViewById(R.id.btn_signin);
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEditText.getText().toString();
                String pwd = pwdEditText.getText().toString();
                //will send request here
                Bundle bundle = new Bundle();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        signInBtn = findViewById(R.id.btn_signup);
    }
}






