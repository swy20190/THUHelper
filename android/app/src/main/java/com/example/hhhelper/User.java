package com.example.hhhelper;

import java.util.ArrayList;

public class User {
    private String uid;
    private String name;
    private String imageID;
    private String account;
    private String mail;
    private String dormitory;
    private String major;
    private int releaseCount;
    private int acceptCount;
    private float score;
    private ArrayList<String> releaseTickets;
    private ArrayList<String> acceptTickets;

    public User(
        String id,
        String name,
        String imageID,
        String account,
        String mail,
        String dormitory,
        String major,
        float score,
        ArrayList<String> releaseTickets,
        ArrayList<String> acceptTickets
    ) {
        this.uid = id;
        this.name = name;
        this.imageID = imageID;
        this.account = account;
        this.mail = mail
        this.dormitory = dormitory;
        this.major = major;
        this.score = score;
        this.releaseCount = releaseTickets.size();
        this.acceptCount = acceptTickets.size();
        this.releaseTickets = releaseTickets;
        this.acceptTickets = acceptTickets;
    }

    public String getImageId() {
        return this.imageID;
    }

    static User initUser(
        String account,
        String name
    ) {
        String id = createBackendUser(account, name);
        if (id == null) {
            return null;
        }
        User u = getBackendUser(id);

        return u;
    }

    static String createBackendUser(
        String account,
        String name
    ) {
        // TODO: send post request to create new ticket

        return "mockUserID";
    }

    static User getBackendUser(String id) {
        // TODO: get request

        // unpack JSON

        // create user at frontend

        String name = "mock name";
        String imageID = "mockImageID";
        String account = "account";
        String mail = "mail";
        String dormitory = "dormitory";
        String major = "major";
        float score = 4.9;
        ArrayList<String> releaseTickets = new ArrayList<>();
        ArrayList<String> acceptTickets = new ArrayList<>();

        User u = new User(
                id,
                name,
                imageID,
                account,
                mail,
                dormitory,
                major,
                score,
                releaseTickets,
                acceptTickets
        );

        return u;
    }


    public String getUserID(){
        return uid;
    }
    public String getNickName(){
        return name;
    }
    public String getMail(){
        return mail;
    }
    public String getDorm(){
        return dormitory;
    }
    public String getAvatarBase64String() {
        return imageID;
    }

    public void setNickName(String nickName){
        this.name = nickName;
    }
    public void setMail(String mail){
        this.mail = mail;
    }
    public void setAvatarBase64String(String base64String){
        this.imageID = base64String;
    }
    public void setDorm(String dorm){
        this.dormitory = dorm;
    }
}
