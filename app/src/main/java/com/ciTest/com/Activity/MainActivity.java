package com.ciTest.com.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ciTest.com.R;

public class MainActivity extends AppCompatActivity {

    private Button b01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AllFIndViewById();
        ButtonSetting();
    }

    private void AllFIndViewById() {
        b01 = findViewById(R.id.b01);
    }

    private void ButtonSetting() {
        b01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ShowPhotoActivity.class);
                startActivity(intent);

            }
        });
    }
}