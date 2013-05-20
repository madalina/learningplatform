package com.madi.learningplatform;

import java.util.Date;

public class Note implements Comparable<Note>{
    private int id = 0;
    private String front = "";
    private String back = "";
    private Date dateAdded = new Date();
    private Boolean learned = false;
    private Boolean remindMe = false;
    
    public Note() {
        
    }
    
    public Note(String front, String back) {
        this.front = front;
        this.back = back;
    }
    
    public Note(int id, String front, String back, Date dateAdded, Boolean learned, Boolean remindMe) {
        this.id = id;
        this.front = front;
        this.back = back;
        this.dateAdded = dateAdded;
        this.learned = learned;
        this.remindMe = remindMe;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public String getFront() {
        return front;
    }
    public void setFront(String front) {
        this.front = front;
    }
    public String getBack() {
        return back;
    }
    public void setBack(String back) {
        this.back = back;
    }
    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
    
    public Boolean getLearned() {
        return learned;
    }

    public void setLearned(Boolean learned) {
        this.learned = learned;
    }
    
    public Boolean getRemindMe() {
        return remindMe;
    }

    public void setRemindMe(Boolean remindMe) {
        this.remindMe = remindMe;
    }

    public String toString() {
        return front + " : " + back + "; id " + id;
    }
    public int compareTo(Note o) {
        if(o.getFront() == null || this.getFront() == null || o == null)
            return -1;
            
        return o.getFront().compareTo(this.getFront());
    }
}
