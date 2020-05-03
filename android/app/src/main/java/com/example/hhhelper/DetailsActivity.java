package com.example.hhhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        String uid = intent.getStringExtra("ticketUID");
        // 使用这个UID进行订单的信息查询
        // 应该需要开一个线程，然后更新ui

    }
}
