package com.madi.learningplatform.controller;

import java.io.IOException;
import java.util.Collections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.madi.learningplatform.NoMoreUnlearnedNotesException;
import com.madi.learningplatform.Note;
import com.madi.learningplatform.State;
import com.madi.learningplatform.service.CollectionService;
import com.madi.learningplatform.service.NoteService;

public class StudyViewController extends AnchorPane  {
    private static final Logger log = LoggerFactory
            .getLogger(StudyViewController.class);
    
    private CollectionService collectionService;
    private NoteService notesService;
    private Note currentNote;
    private int currentNoteIndex = -1;
    
    @FXML private VBox content;
    @FXML private TextArea front;
    @FXML private TextArea back;
    @FXML private Button btnRepeat;
    @FXML private Button btnRepeatLater;
    @FXML private Button btnLearned;
    
    
    public StudyViewController(CollectionService collectionService, NoteService notesService) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/study.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.collectionService = collectionService;
        this.notesService = notesService;
        if(State.getSelectedCollection() == null)
        {
            log.warn("No collection selected");
            return;
        }
        AnchorPane.setLeftAnchor(content, 100.0);
        AnchorPane.setRightAnchor(content, 100.0);
        log.info("Initialized study view controller for collection " + State.getSelectedCollection().getName());
        try
        {
            start();
        }
        catch(NoMoreUnlearnedNotesException ex) {
            log.error(ex.getMessage());
            //TODO happy dialog
        }
    }
    
    private void start() throws NoMoreUnlearnedNotesException {
        if(notesService.unlearnedNotesCurrentCollection.size() == 0)
        {
            throw new NoMoreUnlearnedNotesException();
        }
        Collections.shuffle(notesService.notesCurrentCollection);
        setCurrentNote(0);
        showNote();
        hideOptionsButtons();
    }

    private void setCurrentNote(int i) {
        currentNoteIndex = i;
        currentNote = notesService.notesCurrentCollection.get(currentNoteIndex);
    }

    private void showNote() {
        if(currentNoteIndex >= 0) {
            front.setText(currentNote.getFront());
            back.setText("");
        }
        else {
            front.setText("");
            back.setText("");
        }
    }
    
    public void showAnswer() {
        back.setText(currentNote.getBack());
        showOptionsButtons();
    }
    
    private void showOptionsButtons() {
        btnRepeat.setVisible(true);
        btnRepeatLater.setVisible(true);
        btnLearned.setVisible(true);
    }
    
    private void hideOptionsButtons() {
        btnRepeat.setVisible(false);
        btnRepeatLater.setVisible(false);
        btnLearned.setVisible(false);
    }

    public void showNextNote() {
        try
        {
            setCurrentNote(getNextNoteIndex());
            showNote();
            hideOptionsButtons();
        }
        catch(NoMoreUnlearnedNotesException ex) {
            log.error("Learned all notes in this collection! ");
            //TODO happy dialog
        }
    }
    
    private int getNextNoteIndex() throws NoMoreUnlearnedNotesException {
        if(notesService.unlearnedNotesCurrentCollection.size() == 0)
            throw new NoMoreUnlearnedNotesException();
           
        int index = (currentNoteIndex+1) % notesService.notesCurrentCollection.size();
        while(notesService.notesCurrentCollection.get(index).getLearned())
            index = (currentNoteIndex+2) % notesService.notesCurrentCollection.size();
        return index;
    }

    public void clean() {
        setCurrentNote(-1);
        showNote();
    }
    
    public void learnedHandler() {
        notesService.markLearned(currentNote.getId());
        currentNote.setLearned(true);
        showNextNote();
    }
    
    public void repeatSoonHandler() {
        showNextNote();
    }
    
    public void repeatLaterHandler() {
        showNextNote();
    }

}
