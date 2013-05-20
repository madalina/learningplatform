package com.madi.learningplatform.controller;

import java.io.IOException;
import java.util.Collections;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
        
        final EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    showAnswer();
                }
                else if (keyEvent.getCode() == KeyCode.NUMPAD1) {
                    repeatSoonHandler();
                }
                else if (keyEvent.getCode() == KeyCode.NUMPAD2) {
                        repeatLaterHandler();
                    }
                else if (keyEvent.getCode() == KeyCode.NUMPAD3) {
                    learnedHandler();
                }
            }
        };

        this.setOnKeyReleased(keyEventHandler);

        start();
    }
    
    private void start() {
        Collections.shuffle(notesService.unlearnedNotesCurrentCollection);
        setCurrentNote(0);
        showNote();
        hideOptionsButtons();
    }

    private void setCurrentNote(int i) {
        currentNoteIndex = i;
        currentNote = notesService.unlearnedNotesCurrentCollection.get(currentNoteIndex);
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
        
        if(!collectionService.isSelectedCollectionRelevantForStudy())
        {
            State.getMainApp().showInfoDialog("Congrats! You learned all the notes in this collection!");
            return;
        }

        Collections.shuffle(notesService.unlearnedNotesCurrentCollection);
        setCurrentNote(getNextNoteIndex());
        showNote();
        hideOptionsButtons();
    }
    
    private int getNextNoteIndex() {
        int index = (currentNoteIndex+1) % notesService.unlearnedNotesCurrentCollection.size();
        int loop = 0; // safety net in case of infinite loops
        while(notesService.unlearnedNotesCurrentCollection.get(index).getLearned() && loop <= 10000)
        {
            index = (currentNoteIndex+2) % notesService.unlearnedNotesCurrentCollection.size();
            loop++;
        }
        
        if(loop == 10000)
        {
            log.error("Infinite loop detected while trying to fetch the next unlearned note");
        }
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
        log.debug("Marked as learned");
    }
    
    public void repeatSoonHandler() {
        showNextNote();
        log.debug("Added to repeat soon queue");
    }
    
    public void repeatLaterHandler() {
        notesService.addToRemindLaterQueue(currentNote.getId());
        showNextNote();
        log.debug("Added to reminder queue");
    }

}
