package com.madi.learningplatform.service;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.madi.learningplatform.Collection;
import com.madi.learningplatform.Note;
import com.madi.learningplatform.State;
import com.madi.learningplatform.exceptions.CollectionNotFoundException;
import com.madi.learningplatform.exceptions.NoteNotFoundException;

public class NoteService {
    private static final Logger log = LoggerFactory
            .getLogger(NoteService.class);
    public ObservableList<Note> notesCurrentCollection = FXCollections
            .observableArrayList();
    public ObservableList<Note> unlearnedNotesCurrentCollection = FXCollections
            .observableArrayList();

    public NoteService() {

    }

    public void loadNotesInCollection(int collectionId) throws IOException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = State.getDatabaseConn().createStatement();
            resultSet = statement
                    .executeQuery("select * from notes where collection = " + collectionId);

            while (resultSet.next()) {
                Date d = (Date) resultSet.getDate("dateAdded");
                if (d == null)
                    d = new Date();

                Note note = new Note(resultSet.getInt("id"), resultSet.getString("front"), resultSet.getString("back"), d,
                        resultSet.getBoolean("learned"), resultSet.getBoolean("remindme"));
                notesCurrentCollection.add(note);

                if (!note.getLearned())
                    unlearnedNotesCurrentCollection.add(note);
                
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        
        notesCurrentCollection.clear();
        unlearnedNotesCurrentCollection.clear();
    }

    public void addNote(Note note, Collection selectedCollection)
            throws CollectionNotFoundException, IllegalArgumentException {
        if (note == null || note.getFront() == null || note.getBack() == null) {
            throw new IllegalArgumentException("Note is invalid. Cannot add it");
        }

        if (selectedCollection == null)
            throw new CollectionNotFoundException();

           //TODO
        /*
        DBCollection table = State.getDatabaseConn().getCollection("notes");
        BasicDBObject document = new BasicDBObject();
        document.put("front", note.getFront());
        document.put("back", note.getBack());
        document.put("collection", selectedCollection.getId());
        document.put("dateAdded", new Date());
        document.put("learned", note.getLearned());
        document.put("remindme", note.getRemindMe());
        table.insert(document);

        // fetch the note with the generated id from the db and add to in-memory
        // collections
        try {
            note = getNote(note.getFront(), note.getBack());
            notesCurrentCollection.add(note);
            if (!note.getLearned())
                unlearnedNotesCurrentCollection.add(note);
        } catch (NoteNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        */

    }

    public void updateNote(Note note) throws NoteNotFoundException {
        if (note == null || note.getId() == 0)
            throw new NoteNotFoundException();

        //TODO
        /*
        DBCollection collection = State.getDatabaseConn()
                .getCollection("notes");

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", note.getId());

        DBCursor cursor = collection.find(whereQuery);
        if (cursor.hasNext()) {
            DBObject newObject = cursor.next();
            newObject.put("front", note.getFront());
            newObject.put("back", note.getBack());
            newObject.put("learned", note.getLearned());
            newObject.put("remindme", note.getRemindMe());
            collection.findAndModify(whereQuery, newObject);
        }

        if (!note.getLearned())
            unlearnedNotesCurrentCollection.remove(note); */
    }

    public void deleteNote(Note selectedNote) throws NoteNotFoundException {
        //TODO
        
        if (selectedNote == null || selectedNote.getId() == 0)
            throw new NoteNotFoundException();

        /*
        DBCollection collection = State.getDatabaseConn()
                .getCollection("notes");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", selectedNote.getId());

        DBCursor cursor = collection.find(whereQuery);
        if (cursor.hasNext()) {
            collection.remove(cursor.next());
        } else
            throw new NoteNotFoundException();
            */
    }

    public int countNotesInCollection(int collectionId) {
        //TODO
        return 0;
        /*
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("collection", collectionId);
        return State.getDatabaseConn().getCollection("notes").find(whereQuery)
                .size();
                */
    }

    public int countUnlearnedNotesInCollection(int collectionId) {
        return 0;
        //TODO
        /*
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("collection", collectionId);
        whereQuery.put("learned", false);
        return State.getDatabaseConn().getCollection("notes").find(whereQuery)
                .size();*/
    }

    public void markLearned(int noteId) {
        //TODO
        /*
        DBCollection table = State.getDatabaseConn().getCollection("notes");

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", noteId);

        DBCursor cursor = table.find(whereQuery);
        if (cursor.hasNext()) {
            DBObject newObject = cursor.next();
            newObject.put("learned", true);
            table.findAndModify(whereQuery, newObject);
        }

        Note note = null;
        for (int i = 0; i < unlearnedNotesCurrentCollection.size(); i++) {
            if (unlearnedNotesCurrentCollection.get(i).getId().equals(noteId)) {
                note = unlearnedNotesCurrentCollection.get(i);
                break;
            }
        }

        if (note != null)
            unlearnedNotesCurrentCollection.remove(note);
        */
    }

    /**
     * Delete all notes from the given collection
     */
    public void deleteAllNotes(int collectionId) {
        //TODO
        /*
        BasicDBObject notesQuery = new BasicDBObject();
        notesQuery.put("collection", collectionId);

        DBCursor cursor = State.getDatabaseConn().getCollection("notes")
                .find(notesQuery);
        int index = 0;
        while (cursor.hasNext()) {
            State.getDatabaseConn().getCollection("notes")
                    .remove(cursor.next());
            index++;
        }
        log.info("Removed " + index + " notes");
         */
    }

    public Note getNote(String front, String back) throws NoteNotFoundException {
        //TODO
        
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = State.getDatabaseConn().createStatement();
            resultSet = statement
                    .executeQuery("select * from notes where front = " + front + " and back = " + back);

            if (resultSet.next()) {
                return new Note(resultSet.getInt("id"), resultSet.getString("front"), resultSet.getString("back"),
                        resultSet.getDate("dateAdded"), resultSet.getBoolean("learned"), resultSet.getBoolean("remindme"));
                
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
      
        throw new NoteNotFoundException();
    }

    public void addToRemindLaterQueue(int noteId) {
        updateRemindMeLaterQueue(noteId, true);
    }

    public void removeFromRemindLaterQueue(int noteId) {
        updateRemindMeLaterQueue(noteId, false);
    }

    private void updateRemindMeLaterQueue(int noteId, boolean b) {
        //TODO
        /*
        DBCollection table = State.getDatabaseConn().getCollection("notes");

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", noteId);

        DBCursor cursor = table.find(whereQuery);
        if (cursor.hasNext()) {
            DBObject newObject = cursor.next();
            newObject.put("remindme", b);
            table.findAndModify(whereQuery, newObject);
        }*/

    }

}
