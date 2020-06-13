package com.example.hhhelper;

import java.util.ArrayList;

public class Chat {
    private String ticketID;
    private String senderID;
    private String receiverID;
    private ArrayList<Msg> msgQueue;

    public Chat(String ticketID){
        this.ticketID = ticketID;
        //mock
        this.senderID = "uid114514";
        this.receiverID = "uid1919810";
        this.msgQueue = new ArrayList<>();
        msgQueue.add(new Msg("uid114514","mas1"));
        msgQueue.add(new Msg("uid114514","msg2"));
        msgQueue.add(new Msg("uid1919810","msg3"));
        msgQueue.add(new Msg("uid114514","msg4"));
    }
    public String getSenderID(){
        return this.senderID;
    }
    public String getReceiverID(){
        return this.receiverID;
    }
    public ArrayList<Msg> getMsgQueue(){
        return this.msgQueue;
    }
}
