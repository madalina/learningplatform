package com.madi.learningplatform;

import java.util.HashSet;
import java.util.Set;

import org.bson.types.ObjectId;

public class Collection implements Comparable<Collection>{
    private String name;
    private Set<Note> notes = new HashSet<Note>();
    private ObjectId id;
   
    public Collection() {
        
    }
    
    public Collection(ObjectId id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Collection(String name) {
        this.name = name;
    }
    
    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String newName) {
        name = newName;
    }
    
    public Set<Note> getNotes() {
        return notes;
    }
    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }
    
    public String toString() {
        return name;
    }
    
    public int compareTo(Collection o) {
        return o.getName().compareTo(this.getName());
    }
    public void addNote(Note note) {
        notes.add(note);
    }
}
