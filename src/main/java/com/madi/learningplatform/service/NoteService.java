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
import com.madi.learningplatform.CollectionNotFoundException;
import com.madi.learningplatform.Note;
import com.madi.learningplatform.NoteNotFoundException;
import com.madi.learningplatform.State;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class NoteService {
    private static final Logger log = LoggerFactory
            .getLogger(NoteService.class);
    public ObservableList<Note> notesCurrentCollection = FXCollections
            .observableArrayList();
    public ObservableList<Note> unlearnedNotesCurrentCollection = FXCollections
            .observableArrayList();

    public NoteService() {

    }

    public void loadNotesInCollection(ObjectId collectionId)
            throws JsonParseException, JsonMappingException, IOException {
        DBCollection collection = State.getDatabaseConn()
                .getCollection("notes");

        notesCurrentCollection.clear();
        unlearnedNotesCurrentCollection.clear();

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("collection", collectionId);
        DBCursor cursor = collection.find(whereQuery);
        while (cursor.hasNext()) {
            DBObject row = cursor.next();

            Date d = (Date) row.get("dateAdded");
            if (d == null)
                d = new Date();

            Note note = new Note((ObjectId) row.get("_id"), row.get("front")
                    .toString(), row.get("back").toString(), d,
                    (Boolean) row.get("learned"), (Boolean) row.get("remindme"));
            notesCurrentCollection.add(note);

            if (!note.getLearned())
                unlearnedNotesCurrentCollection.add(note);
        }
    }

    public void addNote(Note note, Collection selectedCollection)
            throws CollectionNotFoundException, IllegalArgumentException {
        if (note == null || note.getFront() == null || note.getBack() == null) {
            throw new IllegalArgumentException("Note is invalid. Cannot add it");
        }

        if (selectedCollection == null)
            throw new CollectionNotFoundException();

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

    }

    public void updateNote(Note note) throws NoteNotFoundException {
        if (note == null || note.getId() == null)
            throw new NoteNotFoundException();

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
            unlearnedNotesCurrentCollection.remove(note);
    }

    public void deleteNote(Note selectedNote) throws NoteNotFoundException {
        if (selectedNote == null || selectedNote.getId() == null)
            throw new NoteNotFoundException();

        DBCollection collection = State.getDatabaseConn()
                .getCollection("notes");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", selectedNote.getId());

        DBCursor cursor = collection.find(whereQuery);
        if (cursor.hasNext()) {
            collection.remove(cursor.next());
        } else
            throw new NoteNotFoundException();
    }

    public int countNotesInCollection(ObjectId collectionId) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("collection", collectionId);
        return State.getDatabaseConn().getCollection("notes").find(whereQuery)
                .size();
    }

    public int countUnlearnedNotesInCollection(ObjectId collectionId) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("collection", collectionId);
        whereQuery.put("learned", false);
        return State.getDatabaseConn().getCollection("notes").find(whereQuery)
                .size();
    }

    public void markLearned(ObjectId noteId) {
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

    }

    /**
     * Delete all notes from the given collection
     */
    public void deleteAllNotes(ObjectId collectionId) {
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
    }

    public Note getNote(String front, String back) throws NoteNotFoundException {
        BasicDBObject notesQuery = new BasicDBObject();
        notesQuery.put("front", front);
        notesQuery.put("back", back);

        DBCursor cursor = State.getDatabaseConn().getCollection("notes")
                .find(notesQuery);
        if (cursor.hasNext()) {
            DBObject row = cursor.next();
            return new Note((ObjectId) row.get("_id"), row.get("front")
                    .toString(), row.get("back").toString(),
                    (Date) row.get("dateAdded"), Boolean.parseBoolean(row.get(
                            "learned").toString()), Boolean.parseBoolean(row
                            .get("remindme").toString()));
        }

        throw new NoteNotFoundException();
    }

    public void addToRemindLaterQueue(ObjectId noteId) {
        updateRemindMeLaterQueue(noteId, true);
    }

    public void removeFromRemindLaterQueue(ObjectId noteId) {
        updateRemindMeLaterQueue(noteId, false);
    }

    private void updateRemindMeLaterQueue(ObjectId noteId, boolean b) {
        DBCollection table = State.getDatabaseConn().getCollection("notes");

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", noteId);

        DBCursor cursor = table.find(whereQuery);
        if (cursor.hasNext()) {
            DBObject newObject = cursor.next();
            newObject.put("remindme", b);
            table.findAndModify(whereQuery, newObject);
        }

    }

}
