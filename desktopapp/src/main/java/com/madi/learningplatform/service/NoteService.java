package com.madi.learningplatform.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.madi.learningplatform.Collection;
import com.madi.learningplatform.DatabaseConnection;
import com.madi.learningplatform.Note;
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
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = DatabaseConnection.getConnection().prepareStatement("select * from notes where collection = ?");
            statement.setInt(1, collectionId);
            resultSet = statement.executeQuery();

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
        PreparedStatement statement = null;
        try {
            statement = DatabaseConnection.getConnection().prepareStatement("insert into notes (front, back, collection, dateAdded, learned, remindme)" +
            		" values (?, ?, ?, ?, ?, ?)");
            
            statement.setString(1, note.getFront());
            statement.setString(2, note.getBack());
            statement.setInt(3, selectedCollection.getId());
            statement.setDate(4, new java.sql.Date( new Date().getTime() ));
            statement.setBoolean(5, note.getLearned());
            statement.setBoolean(6, note.getRemindMe());
            
            statement.executeUpdate();
        }
        catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        
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
        if (note == null || note.getId() == 0)
            throw new NoteNotFoundException();

        PreparedStatement statement = null;
        try {
            statement = DatabaseConnection.getConnection().prepareStatement("update notes set front = ?, back = ?, learned = ?, remindme = ? where id = ?");
            statement.setString(1, note.getFront());
            statement.setString(2, note.getBack());
            statement.setBoolean(3, note.getLearned());
            statement.setBoolean(4, note.getRemindMe());
            statement.setInt(5, note.getId());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
       
        if (!note.getLearned())
            unlearnedNotesCurrentCollection.remove(note);
    }

    public void deleteNote(Note selectedNote) throws NoteNotFoundException {
        if (selectedNote == null || selectedNote.getId() == 0)
            throw new NoteNotFoundException();
        PreparedStatement statement = null;
        try {
            statement = DatabaseConnection.getConnection().prepareStatement("delete from notes where id = ?");
            statement.setInt(1, selectedNote.getId());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    public int countNotesInCollection(int collectionId) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = DatabaseConnection.getConnection().prepareStatement("select count(*) as nr from notes where collection = ?");
            statement.setInt(1, collectionId);
            rs = statement.executeQuery();
            if(rs.next())
                return rs.getInt("nr");
        }
        catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return 0;
    }

    public int countUnlearnedNotesInCollection(int collectionId) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = DatabaseConnection.getConnection().prepareStatement("select count(*) as nr from notes where collection = ? and learned = 0");
            statement.setInt(1, collectionId);
            rs = statement.executeQuery();
            if(rs.next())
                return rs.getInt("nr");
        }
        catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return 0;
    }

    public void markLearned(int noteId) {
        PreparedStatement statement = null;
        try {
            statement = DatabaseConnection.getConnection().prepareStatement("update notes set learned = 1 where id = ?");
            statement.setInt(1, noteId);
            statement.executeUpdate();
            
            Note note = null;
            for (int i = 0; i < unlearnedNotesCurrentCollection.size(); i++) {
                if (unlearnedNotesCurrentCollection.get(i).getId() == noteId) {
                    note = unlearnedNotesCurrentCollection.get(i);
                    break;
                }
            }

            if (note != null)
                unlearnedNotesCurrentCollection.remove(note);
            
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Delete all notes from the given collection
     */
    public void deleteAllNotes(int collectionId) {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = DatabaseConnection.getConnection();
            statement = conn.prepareStatement("delete from notes where collection = ?");
            statement.setInt(1, collectionId);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    public Note getNote(String front, String back) throws NoteNotFoundException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = DatabaseConnection.getConnection().prepareStatement("select * from notes where front = ? and back = ?");
            statement.setString(1, front);
            statement.setString(2, back);
            resultSet = statement.executeQuery();

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
        PreparedStatement statement = null;
        try {
            statement = DatabaseConnection.getConnection().prepareStatement("update notes set remindme = ? where id = ?");
            statement.setBoolean(1, b);
            statement.setInt(2, noteId);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

}
