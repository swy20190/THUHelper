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
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;
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
        currentTicket = Ticket.getBackendTicket(uid);
        String senderID = currentTicket.getReleaseUserID();
        String receiverID = currentTicket.getAcceptUserID();
        if(senderID!=null){
            sender = User.getBackendUser(senderID);
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

        if(receiverID!=null){
            receiver = User.getBackendUser(receiverID);
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
        ticketTitle.setText(currentTicket.getTitle());

        TextView ticketUID = (TextView)findViewById(R.id.detail_ticket_uid);
        ticketUID.setText(currentTicket.getUid());

        TextView ticketContent = (TextView)findViewById(R.id.detail_ticket_content);
        ticketContent.setText(currentTicket.getDescription());

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
        Calendar now = Calendar.getInstance();
        Calendar ddl = currentTicket.getDeadline();
        long seconds = now.getTimeInMillis() - ddl.getTimeInMillis();

        time2DDL.setText("距离DDL还有"+seconds+"秒");

        final Button accept = (Button) findViewById(R.id.detail_exe_button);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myID.equals(currentTicket.getReleaseUserID())){
                    Toast.makeText(DetailsActivity.this,"你不能抢自己的订单",Toast.LENGTH_SHORT).show();
                }else if(!currentTicket.getStatus().equals(Ticket.TicketState.RELEASED)){
                    Toast.makeText(DetailsActivity.this,"订单不可抢",Toast.LENGTH_SHORT).show();
                }else {
                    if (acceptTicket()) {
                        Toast.makeText(DetailsActivity.this, "已抢单", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailsActivity.this, "抢单失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Button cancel = (Button) findViewById(R.id.detail_cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!myID.equals(currentTicket.getReleaseUserID())){
                    Toast.makeText(DetailsActivity.this,"你没有权限删除该订单",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(cancelTicket()){
                        Toast.makeText(DetailsActivity.this,"订单已删除",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(DetailsActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Button ensure = (Button)findViewById(R.id.detail_ensure_button);
        ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!myID.equals(currentTicket.getReleaseUserID())){
                    Toast.makeText(DetailsActivity.this,"您非发布者，没有此权限",Toast.LENGTH_SHORT).show();
                }else{
                    if(ensureTicket()){
                        Toast.makeText(DetailsActivity.this,"订单已完成",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(DetailsActivity.this,"操作失败",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        final Button toChatting = (Button)findViewById(R.id.detail_ticket_tochatting);
        toChatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentTicket.getStatus().equals(Ticket.TicketState.ACCEPTED)||
                currentTicket.getStatus().equals(Ticket.TicketState.TIMEOUT)||
                currentTicket.getStatus().equals(Ticket.TicketState.FINISHED)){
                    toChatting();
                }else{
                    Toast.makeText(DetailsActivity.this,"聊天窗口未开放",Toast.LENGTH_SHORT).show();
                    toChatting(); //测试用
                }
            }
        });


    }

    private boolean acceptTicket(){
        return true;
    }

    private boolean cancelTicket(){
        return true;
    }

    private boolean ensureTicket(){
        return true;
    }

    private void toChatting(){
        Intent intent = new Intent(DetailsActivity.this, ChattingActivity.class);
        intent.putExtra("ticketUID",currentTicket.getUid());
        startActivity(intent);
    }
}
