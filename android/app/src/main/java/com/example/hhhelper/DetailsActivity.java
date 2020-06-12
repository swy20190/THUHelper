package com.example.hhhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
        String senderIDString = currentTicket.getSenderID();
        String receiverIDString = currentTicket.getReceiverID();
        if(senderIDString!=null){
            sender = getUser(senderIDString);
            //头像
            CircleImageView senderAvatar = (CircleImageView)findViewById(R.id.detail_sender_avatar);
            Bitmap bitmap = null;
            try{
                byte[] bitmapByte  = Base64.decode(sender.getAvatarBase64String(), Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(bitmapByte,0,bitmapByte.length);
                senderAvatar.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
            //uid
            TextView senderUID = (TextView)findViewById(R.id.detail_sender_uid);
            senderUID.setText(sender.getUserID());
            //nickname
            TextView senderNickname = (TextView) findViewById(R.id.detail_sender_nickname);
            senderNickname.setText(sender.getNickName());
            //dorm
            TextView senderDorm = (TextView) findViewById(R.id.detail_sender_dorm);
            senderDorm.setText(sender.getDorm());
            //mail
            TextView senderMail = (TextView) findViewById(R.id.detail_sender_mail);
            senderMail.setText(sender.getMail());
        }else{
            View senderInfo = (View) findViewById(R.id.detail_sender_info);
            senderInfo.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,0));
        }

        if(receiverIDString!=null){
            receiver = getUser(senderIDString);
            //头像
            CircleImageView receiverAvatar = (CircleImageView)findViewById(R.id.detail_receiver_avatar);
            Bitmap bitmap = null;
            try{
                byte[] bitmapByte  = Base64.decode(receiver.getAvatarBase64String(), Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(bitmapByte,0,bitmapByte.length);
                receiverAvatar.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
            //uid
            TextView receiverUID = (TextView)findViewById(R.id.detail_receiver_uid);
            receiverUID.setText(receiver.getUserID());
            //nickname
            TextView receiverNickname = (TextView) findViewById(R.id.detail_receiver_nickname);
            receiverNickname.setText(receiver.getNickName());
            //dorm
            TextView receiverDorm = (TextView) findViewById(R.id.detail_receiver_dorm);
            receiverDorm.setText(receiver.getDorm());
            //mail
            TextView receiverMail = (TextView) findViewById(R.id.detail_receiver_mail);
            receiverMail.setText(receiver.getMail());
        }else{
            View receiverInfo = (View) findViewById(R.id.detail_receiver_info);
            receiverInfo.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,0));
        }

        TextView ticketTitle = (TextView)findViewById(R.id.detail_ticket_title);
        ticketTitle.setText(currentTicket.getName());

        TextView ticketUID = (TextView)findViewById(R.id.detail_ticket_uid);
        ticketUID.setText(currentTicket.getUid());

        TextView ticketContent = (TextView)findViewById(R.id.detail_ticket_content);
        ticketContent.setText(currentTicket.getDescriptions());

        TextView ticketStatus = (TextView) findViewById(R.id.detail_ticket_status);
        switch (currentTicket.getStatus()){
            case RELEASED:
                ticketStatus.setText("无人接单");
                break;
            case ACCEPTED:
                ticketStatus.setText("有人接单，未完成");
                break;
            case FINISHED:
                ticketStatus.setText("已完成");
                break;
            case TIMEOUT:
                ticketStatus.setText("已超时");
                break;
            case TERMINATED:
                ticketStatus.setText("已关闭");
                break;
            default:
                break;
        }

        TextView time2DDL = (TextView) findViewById(R.id.detail_ticket_t2ddl);
        Date now = new Date();
        Date ddl = currentTicket.getDeadline();
        long seconds = (ddl.getTime()-now.getTime())/1000;
        time2DDL.setText("距离DDL还有"+seconds+"秒");



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
