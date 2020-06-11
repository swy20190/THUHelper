package com.example.hhhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailsActivity extends AppCompatActivity {
    private Ticket currentTicket;
    private SharedPreferences preferences;
    private String myID;
    private User sender;
    private User receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        myID = preferences.getString("userID","");
        Intent intent = getIntent();
        String uid = intent.getStringExtra("ticketUID");
        // 使用这个UID进行订单的信息查询
        // 应该需要开一个线程，然后更新ui
        // 或者布局静态，点击按钮的时候报error（比如你不能抢自己的订单，你不能删别人的订单）
        currentTicket = getCurrentTicket(uid);
        final TextView viewUid = (TextView) findViewById(R.id.detail_ticket_uid);
        viewUid.setText("uid: "+currentTicket.getUid());
        Button backButton = (Button) findViewById(R.id.detail_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DetailsActivity.this,MainActivity.class);
                startActivity(intent1);
            }
        });
        final TextView senderID = (TextView) findViewById(R.id.detail_sender_uid); //这个id决定你与谁聊天
        CircleImageView senderAvatar = (CircleImageView)findViewById(R.id.detail_sender_avatar);
        senderAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToChat = new Intent(DetailsActivity.this,ChattingActivity.class);
                //其实这里应该是用订单的id从服务器网络请求得到用户的senderID，然后将显示控件置为该ID，这里为了方便从控件get得到ID
                intentToChat.putExtra("partnerID",senderID.getText().toString());
                startActivity(intentToChat);
            }
        });
        //这里是动态决定可以和谁聊天的，毕竟你或是sender,或是receiver,不可能与这两个人同时聊天。
        final TextView receiverID = (TextView) findViewById(R.id.detail_receiver_uid);
        CircleImageView receiverAvatar = (CircleImageView) findViewById(R.id.detail_receiver_avatar);
        receiverAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToChat = new Intent(DetailsActivity.this,ChattingActivity.class);
                intentToChat.putExtra("partnerID",receiverID.getText().toString());
                startActivity(intentToChat);
            }
        });
    }

    private Ticket getCurrentTicket(String ticketID){
        Ticket ticket = new Ticket("mock ticket");
        ticket.setBonus("$810");
        ticket.setSenderID("sender114514");
        ticket.setReceiverID("receiver114514");
        ticket.setDeadline(new Date());
        return ticket;
    }

    private User getUser(String userID){
        User user = new User(userID);
        user.setDorm("zz220");
        user.setMail("ss@zz");
        return user;
    }
}
