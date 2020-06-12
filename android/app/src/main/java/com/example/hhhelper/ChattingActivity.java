package com.example.hhhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ChattingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        Intent intent = getIntent();
        String partnerName = intent.getStringExtra("ticketUID");// 这里应该是通过id查找到昵称
        TextView chatter = (TextView) findViewById(R.id.chatting_partner_name);
        chatter.setText(partnerName);
    }
}
