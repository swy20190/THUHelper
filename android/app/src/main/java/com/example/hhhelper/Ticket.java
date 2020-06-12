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
    public enum Status{
        RELEASED, //not accepted yet
        ACCEPTED,
        TIMEOUT, //deadline missed, but not terminated
        FINISHED, //finished
        TERMINATED
    }
    private Status status;
    private String descriptions; //详细内容


    public Ticket(String name){
        this.name = name;
        this.uid = name;//这里我暂时用name兼职一下uid
        this.bonus = "$1919";
        this.deadline = new Date();
        this.descriptions = "114,514!114,514!";
        this.status = Status.RELEASED;
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
    public String getSenderID(){
        return this.senderID;
    }
    public String getReceiverID(){
        return this.receiverID;
    }
    public String getDescriptions(){
        return this.descriptions;
    }
    public Status getStatus(){
        return this.status;
    }

    public void setSenderImageBase64(String senderImageBase64){
        this.senderImageBase64 = senderImageBase64;
    }
    public void setBonus(String bonus){
        this.bonus = bonus;
    }
    public void setDeadline(Date deadline){
        this.deadline = deadline;
    }
    public void setSenderID(String id){
        this.senderID = id;
    }
    public void setReceiverID(String id){
        this.receiverID = id;
    }
    public void setStatus(Status status){
        this.status = status;
    }

}
