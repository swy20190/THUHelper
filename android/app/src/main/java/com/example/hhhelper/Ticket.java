package com.example.hhhelper;

import java.util.*;

public class Ticket {
    public enum TicketState {
        RELEASED,
        ACCEPTED,
        TIMEOUT,
        FINISHED,
        INVALID,
        TERMINATED
    }

    // fields
    private String uid;
    private String title = "";
    private String releaseUserID;
    private String acceptUserID;
    private Calendar createTime;
    private TicketState state = TicketState.RELEASED;
    private int bonus = 0;
    private Calendar deadline = null;
    private String location = "";
    private String description = "";
    private String feedback = "";
    private int score = -1;
//    private LinkedList<Update> updateQueue;
    private int imageId = -1;

    // constructors, getters, setters
//    public Ticket(String name, int imageId){
//        this.name = name;
//        this.uid = null;//这里我暂时用name兼职一下uid
//        this.imageId = imageId;
//    }

    public Ticket(
        String id,
        String title,
        String releaseUserID,
        String acceptUserID,
        Calendar createTime,
        TicketState state,
        int bonus,
        Calendar deadline,
        String location,
        String description,
        String feedback,
        int score
    ) {
        this.uid = id;
        this.title = title;
        this.releaseUserID = releaseUserID;
        this.acceptUserID = acceptUserID;
        this.createTime = createTime;
        this.state = state;
        this.bonus = bonus;
        this.deadline = deadline;
        this.location = location;
        this.description = description;
        this.feedback = feedback;
        this.score = score;
    }

    // initTicket: init a ticket at backend, then return the ticket created
    static Ticket initTicket(
            String title, // title
            String releaseUserID, // release user
            String description // description of the mission
    ) {
        String id = createBackendTicket(title, releaseUserID, description);
        if (id == null) { // failed
            return null;
        }
        Ticket t = getBackendTicket(id);
        return t;
    }

    // create a ticket at backend, if success, return ticket ID.
    static String createBackendTicket(
            String title, // title
            String releaseUserID, // release user
            String description // description of the mission
    ) {
        // TODO:POST method


        // return mock id
        return "mockTicketID";
    }

    static Ticket getBackendTicket(String id) {
        // send a get request to get ticket information

        // TODO:GET method

        String title = "mock name";
        String releaseUserID = "mockUserID" +(int)(Math.random()*10000);
        String acceptUserID = "mockUserID" +(int)(Math.random()*10000);
        Calendar createTime = Calendar.getInstance();
        TicketState state = TicketState.RELEASED;
        int bonus = 5;
        Calendar deadline = Calendar.getInstance();
        deadline.add(Calendar.DATE, 7);
        String location = "somewhere";
        String description = "a description";
        String feedback = null;
        int score = -1;
        // create a ticket at frontend
        Ticket t = new Ticket(
                id,
                title,
                releaseUserID,
                acceptUserID,
                createTime,
                state,
                bonus,
                deadline,
                location,
                description,
                feedback,
                score
        );
        return t;
    }

    static ArrayList<String> getBackendTicketIdList(int n) {
        // TODO:GET method
        ArrayList<String> l = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            l.add("mockTicketID" + String.valueOf(i));
        }
        return l;
    }

    public String getUid(){
        return uid;
    }
    public String getTitle(){
        return title;
    }
    public String getImageId(){
        return User.getBackendUser(releaseUserID).getImageId();
    }
    public int getBonus(){
        return bonus;
    }
    public Calendar getDeadline(){
        return deadline;
    }
    public String getReleaseUserID(){
        return this.releaseUserID;
    }
    public String getAcceptUserID(){
        return this.acceptUserID;
    }
    public String getDescription(){
        return this.description;
    }
    public TicketState getStatus(){
        return this.state;
    }
    public void setBonus(int bonus){
        this.bonus = bonus;
    }
    public void setDeadline(Calendar deadline){
        this.deadline = deadline;
    }
    public void setAcceptUserID(String id){
        this.acceptUserID = id;
    }
    public void setStatus(TicketState status){
        this.state = status;
    }

}
