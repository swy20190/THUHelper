package com.example.hhhelper;

import java.util.Date;

public class Ticket {
    private String uid;
    private String name; //标题
    private Date deadline; //ddl
    private Date createTime;
    private String bonus;
    private String senderImageBase64; //发布者头像
    private String senderID;
    private String receiverID;
    private int ticketStatus;


    public Ticket(String name){
        this.name = name;
        this.uid = name;//这里我暂时用name兼职一下uid
        this.bonus = "$1919";
        this.deadline = new Date();
    }
    public String getName(){
        return name;
    }
    public String getUid(){
        return uid;
    }
    public String getSenderImageBase64(){
        return this.senderImageBase64;
    }
    public String getBonus(){
        return this.bonus;
    }
    public Date getDeadline(){
        return deadline;
    }
    public void setSenderImageBase64(String senderImageBase64){
        this.senderImageBase64 = senderImageBase64;
    }
    public void setBonus(String bonus){
        this.bonus = bonus;
    }
}
