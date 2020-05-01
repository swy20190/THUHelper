package com.example.hhhelper;

public class Ticket {
    private String name;
    private int imageId;
    public Ticket(String name, int imageId){
        this.name = name;
        this.imageId = imageId;
    }
    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
}
