package com.example.hhhelper;

public class Ticket {
    private String uid;
    private String name;
    private int imageId;
    public Ticket(String name, int imageId){
        this.name = name;
        this.uid = name;//这里我暂时用name兼职一下uid
        this.imageId = imageId;
    }
    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
    public String getUid(){
        return uid;
    }
}
