package com.example.hhhelper;

public class User {
    private String userID;
    private String nickName;
    private String mail;
    private String dorm;
    private String avatarBase64String;

    public User(String userID){
        this.userID = userID;
        this.nickName = "nick"+(int)(Math.random()*20);
    }
    public String getUserID(){
        return userID;
    }
    public String getNickName(){
        return nickName;
    }
    public String getMail(){
        return mail;
    }
    public String getDorm(){
        return dorm;
    }
    public String getAvatarBase64String() {
        return avatarBase64String;
    }

    public void setNickName(String nickName){
        this.nickName = nickName;
    }
    public void setMail(String mail){
        this.mail = mail;
    }
    public void setAvatarBase64String(String base64String){
        this.avatarBase64String = base64String;
    }
    public void setDorm(String dorm){
        this.dorm = dorm;
    }
}
