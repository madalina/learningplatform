package com.madi.learningplatform.service;

import java.io.IOException;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.madi.learningplatform.Collection;
import com.madi.learningplatform.Note;
import com.madi.learningplatform.State;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class NoteService {
    private static final Logger log = LoggerFactory
            .getLogger(NoteService.class);
    public ObservableList<Note> notesCurrentCollection;

    public NoteService() {

    }

    public void loadNotesCurrentCollection(ObjectId collectionId)
            throws JsonParseException, JsonMappingException, IOException {
        DBCollection collection = State.getDatabaseConn()
                .getCollection("notes");
        if (notesCurrentCollection == null)
            notesCurrentCollection = FXCollections.observableArrayList();
        notesCurrentCollection.clear();

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("collection", collectionId);
        DBCursor cursor = collection.find(whereQuery);
        while (cursor.hasNext()) {
            DBObject row = cursor.next();
            
            Date d = (Date)row.get("dateAdded");
            if(d == null)
                d = new Date();
            Note note = new Note((ObjectId)row.get("_id"), row.get("front").toString(), row.get("back").toString(), d);
            notesCurrentCollection.add(note);
        }
    }

    public void addNote(Note note, Collection selectedCollection) {
        if (note == null) {
            log.error("Cannot add a null note");
            return;
        }

        notesCurrentCollection.add(note);

        DBCollection table = State.getDatabaseConn().getCollection("notes");
        BasicDBObject document = new BasicDBObject();
        document.put("front", note.getFront());
        document.put("back", note.getBack());
        document.put("collection", selectedCollection.getId());
        document.put("dateAdded", new Date());
        table.insert(document);
    }

    public void updateNote(Note updatedNote) {
        DBCollection collection = State.getDatabaseConn().getCollection("notes");
        
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", updatedNote.getId());
        
        DBCursor cursor = collection.find(whereQuery);
        if(cursor.hasNext())
        {
            DBObject newObject =  cursor.next();
            newObject.put("front", updatedNote.getFront());
            newObject.put("back", updatedNote.getBack());
            collection.findAndModify(whereQuery, newObject);
        }
    }

    public void deleteNote(Note selectedNote) {
        DBCollection collection = State.getDatabaseConn()
                .getCollection("notes");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", selectedNote.getId());
        
        DBCursor cursor = collection.find(whereQuery);
        while (cursor.hasNext()) {
            collection.remove(cursor.next());
        }
    }

    public int countNotesInCollection(ObjectId collectionId) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("collection", collectionId);
        return State.getDatabaseConn().getCollection("notes").find(whereQuery).size();
    }

}
