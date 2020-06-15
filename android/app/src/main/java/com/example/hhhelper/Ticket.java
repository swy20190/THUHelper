package com.example.hhhelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
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
    private String bonus = "";
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
        String bonus,
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
            String bonus, // bonus
            String ddl, // yy-mm-dd-hh-mm-ss
            String description // description of the mission
    ) {
        String id = createBackendTicket(title, releaseUserID, bonus, ddl, description);
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
            String bonus, // bonus of the mission
            String ddl, // ddl of the mission, yy-mm-dd-hh-mm-ss
            String description //description of the mission
    ) {
        // TODO:POST method
//        BackendRequest r = new BackendRequest("/ticket");
        BackendRequest r = new BackendRequest("/test"); // only for test
//        JSONObject j = new JSONObject();
//        JSONObject ret;
//        try {
//            j.putOpt("title", title);
//            j.putOpt("releaseUserID", releaseUserID);
//            j.putOpt("description", description);
//            ret = r.post((j));
//            return ret.optString("id", null);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }


        // return mock id
        return "mockTicketID";
    }

    static Ticket getBackendTicket(String id) {
        // send a get request to get ticket information
//        BackendRequest r = new BackendRequest("/ticket" + id);
        BackendRequest r = new BackendRequest("/test"); // only for test
//        JSONObject j = r.get();
//
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        Date tmpDate;
//
//        String name = j.optString("name", null);
//        String releaseUserID = j.optString("releaseUserID", null);
//        String acceptUserID = j.optString("acceptUserID", null);
//        String createTimeString = j.optString("createTime", null);
//        Calendar createTime;
//        try {
//            tmpDate = sdf.parse(createTimeString);
//            createTime = Calendar.getInstance();
//            createTime.setTime(tmpDate);
//        } catch (Exception e) {
//            e.printStackTrace();
//            createTime = null;
//        }
//        String stateString = j.optString("state", "INVALID");
//        TicketState state = TicketState.valueOf(stateString);
//        String bonus = j.optString("bonus", null);
//        String deadlineString = j.optString("deadline", null);
//        Calendar deadline;
//        try {
//            tmpDate = sdf.parse(deadlineString);
//            deadline = Calendar.getInstance();
//            deadline.setTime(tmpDate);
//        } catch (Exception e) {
//            e.printStackTrace();
//            createTime = null;
//        }
//        String location = j.optString("location", null);
//        String description = j.optString("description", null);
//        String feedback = j.optString("feedback", null);
//        int score = j.optInt("score", -1);

        String title = "mock name";
        String releaseUserID = "mockUserID" +(int)(Math.random()*10000);
        String acceptUserID = "mockUserID" +(int)(Math.random()*10000);
        Calendar createTime = Calendar.getInstance();
        TicketState state = TicketState.RELEASED;
        String bonus = "";
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

    static ArrayList<String> getBackendTicketLatestList(int n) {
        // TODO:GET method
//        BackendRequest r = new BackendRequest("/ticket/latest" + String.valueOf(n));
        BackendRequest r = new BackendRequest("/test"); // only for test
        JSONObject j = r.get();
        JSONArray t = j.optJSONArray("tickets");

        ArrayList<String> l = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            l.add(t.optString(i, null));
        }
        return l;
    }

    static ArrayList<String> getBackendTicketReceiveList(int n, String id) {
        // TODO:GET method
//        BackendRequest r = new BackendRequest("/ticket/accept/" + id + "/" + String.valueOf(n));
        BackendRequest r = new BackendRequest("/test"); // only for test
        JSONObject j = r.get();
        JSONArray t = j.optJSONArray("tickets");

        ArrayList<String> l = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            l.add(t.optString(i, null));
        }
        return l;
    }

    static ArrayList<String> getBackendTicketAcceptList(int n, String id) {
        // TODO:GET method
//        BackendRequest r = new BackendRequest("/ticket/accept/" + id + "/" + String.valueOf(n));
        BackendRequest r = new BackendRequest("/test"); // only for test
        JSONObject j = r.get();
        JSONArray t = j.optJSONArray("tickets");

        ArrayList<String> l = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            l.add(t.optString(i, null));
        }
        return l;
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
    public String getBonus(){
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
    public void setBonus(String bonus){
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
