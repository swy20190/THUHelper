package com.example.hhhelper;

public class Msg {
    private String authorID;
    private String content;
    private boolean createdByMe;

    public Msg(String authorID, String content){
        this.authorID = authorID;
        this.content = content;
        this.createdByMe = false;
    }
    public String getAuthorID(){
        return this.authorID;
    }
    public boolean getCreatedByMe(){
        return createdByMe;
    }
    public String getContent(){
        return content;
    }
    public void setCreatedByMe(boolean created){
        this.createdByMe = created;
    }
    public void setContent(String content){
        this.content = content;
    }
}
