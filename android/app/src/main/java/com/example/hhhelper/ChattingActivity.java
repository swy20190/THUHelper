package com.example.hhhelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import java.util.ArrayList;

public class ChattingActivity extends AppCompatActivity {
    private String ticketUID;
    private String myID;
    private String partnerID;
    private Chat currentChat;
    private SharedPreferences preferences;
    private ArrayList<Msg> mMsgQueue;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        myID = preferences.getString("userID","");
        Intent intent = getIntent();
        ticketUID = intent.getStringExtra("ticketUID");
        currentChat = getChat(ticketUID);
        if(currentChat.getSenderID().equals(myID)){
            partnerID = currentChat.getReceiverID();
        }else{
            partnerID = currentChat.getSenderID();
        }
        TextView banner = (TextView)findViewById(R.id.chatting_partner_name);
        banner.setText("与"+partnerID+"聊天");

        initQueue();

        msgRecyclerView = (RecyclerView)findViewById(R.id.chatting_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(mMsgQueue);
        msgRecyclerView.setAdapter(adapter);
    }

    private Chat getChat(String ticketUID){
        Chat chat = new Chat(ticketUID);
        return chat;
    }

    private void initQueue(){
        Chat tmp = getChat(myID);
        mMsgQueue = tmp.getMsgQueue();
        for(int i=0;i<mMsgQueue.size();i++){
            if(mMsgQueue.get(i).getAuthorID().equals(myID)){
                mMsgQueue.get(i).setCreatedByMe(true);
            }
            else{
                mMsgQueue.get(i).setCreatedByMe(false);
            }
        }
    }
}
