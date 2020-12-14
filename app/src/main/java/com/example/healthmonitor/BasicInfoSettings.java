package com.example.healthmonitor;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class BasicInfoSettings extends AppCompatActivity {
   private Button saveBtn;
   private EditText nickName;
   private EditText age;
   private EditText weight;
   private EditText height;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_settings);
        saveBtn =findViewById(R.id.btn_save_basicInfo);
        nickName  = findViewById(R.id.edittext_nickname);
        age  = findViewById(R.id.edittext_Age);
        weight = findViewById(R.id.edittext_Weight);
        height = findViewById(R.id.edittext_Height);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(BasicInfoSettings.this,"Submitted",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
