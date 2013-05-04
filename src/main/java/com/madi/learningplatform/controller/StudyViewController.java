package com.madi.learningplatform.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    
    @FXML private Button btn;
    @FXML private VBox content;
    @FXML private TextArea front;
    @FXML private TextArea back;
    
    
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
        start();
    }
    
    private void start() {
        if(notesService.notesCurrentCollection.size() == 0)
            return;
        setCurrentNote(0);
        showNote();
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
    
    public void showBack() {
        back.setText(currentNote.getBack());
    }
    
    public void showNextNote() {
        setCurrentNote((currentNoteIndex+1) % notesService.notesCurrentCollection.size());
        showNote();
    }
    
    public void clean() {
        setCurrentNote(-1);
        showNote();
    }

}
