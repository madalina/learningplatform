package com.madi.learningplatform;

import java.util.Date;

import org.bson.types.ObjectId;

public class Note implements Comparable<Note>{
    private ObjectId id;
    private String front;
    private String back;
    private Date dateAdded;
    
    public Note() {
        
    }
    
    public Note(String front, String back) {
        this.front = front;
        this.back = back;
    }
    
    public Note(ObjectId id, String front, String back, Date dateAdded) {
        this.id = id;
        this.front = front;
        this.back = back;
        this.dateAdded = dateAdded;
    }
    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
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

    
    public String toString() {
        return front + " : " + back + "; id " + id;
    }
    public int compareTo(Note o) {
        return o.getFront().compareTo(this.getFront());
    }
}
