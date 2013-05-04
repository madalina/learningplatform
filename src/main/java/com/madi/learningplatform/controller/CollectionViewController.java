package com.madi.learningplatform.controller;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.madi.learningplatform.Note;
import com.madi.learningplatform.State;
import com.madi.learningplatform.service.CollectionService;
import com.madi.learningplatform.service.NoteService;

public class CollectionViewController extends AnchorPane {
    private static final Logger log = LoggerFactory
            .getLogger(CollectionViewController.class);

    private CollectionService collectionService;
    private NoteService notesService;
    private DisplayScreenController parentController;

    @FXML
    protected VBox content;
    @FXML
    protected ScrollPane notesScrollPane;
    @FXML
    protected TableView<Note> notesTableView;
    @FXML
    protected TableColumn<Note, String> frontColumn;
    @FXML
    protected TableColumn<Note, String> backColumn;
    @FXML
    protected TableColumn<Note, String> dateAddedColumn;
    @FXML
    protected TextArea noteFront;
    @FXML
    protected TextArea noteBack;
    @FXML
    protected VBox noteDetails;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public CollectionViewController(DisplayScreenController parentController,
            CollectionService collectionService, NoteService notesService) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/collectionview.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        AnchorPane.setLeftAnchor(content, 50.0);
        AnchorPane.setRightAnchor(content, 50.0);
        notesScrollPane.setFitToWidth(true);

        this.parentController = parentController;
        this.collectionService = collectionService;
        this.notesService = notesService;
        log.info("Initialized collection view controller for collection "
                + State.getSelectedCollection().getName());

        try {
            noteFront.setText("");
            noteBack.setText("");

            notesTableView.getSelectionModel().selectedItemProperty()
                    .addListener(new ChangeListener() {
                        public void changed(ObservableValue observable,
                                Object oldValue, Object newValue) {
                            Note selectedNote = (Note) newValue;
                            if (newValue != null) {
                                noteFront.setText(selectedNote.getFront());
                                noteBack.setText(selectedNote.getBack());
                            }
                        }
                    });

            frontColumn
                    .setCellValueFactory(new PropertyValueFactory<Note, String>(
                            "front"));
            backColumn
                    .setCellValueFactory(new PropertyValueFactory<Note, String>(
                            "back"));
            dateAddedColumn
                    .setCellValueFactory(new PropertyValueFactory<Note, String>(
                            "dateAdded"));
            notesTableView.setItems(notesService.notesCurrentCollection);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void addNewNoteHandler() {
        if (State.getSelectedCollection() == null) {
            log.error("No collection selected.");
            return;
        }

        final Stage addNewNoteDialog = new Stage();
        addNewNoteDialog.initModality(Modality.WINDOW_MODAL);

        final TextField noteFront = new TextField();
        final TextField noteBack = new TextField();
        final Scene addNewNoteScene;

        final EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {

                    if (noteFront.getText().isEmpty()) {
                        log.error("Note needs to have the front filled in");
                        return;
                    }
                    String front = noteFront.getText();
                    String back = noteBack.getText();
                    notesService.addNote(new Note(front, back),
                            State.getSelectedCollection());
                    try {
                        refreshNotesContainer();
                    } catch (JsonParseException e) {
                        log.error(e.getMessage(), e);
                    } catch (JsonMappingException e) {
                        log.error(e.getMessage(), e);
                    } catch (IOException e) {
                        log.error(e.getMessage(), e);
                    }
                    keyEvent.consume();
                    addNewNoteDialog.close();
                    parentController.refreshCollectionList();
                }
            }
        };

        noteFront.setOnKeyReleased(keyEventHandler);
        noteBack.setOnKeyReleased(keyEventHandler);

        addNewNoteScene = new Scene(VBoxBuilder.create()
                .children(noteFront, noteBack).alignment(Pos.CENTER)
                .padding(new Insets(10)).build());

        addNewNoteDialog.setTitle("Add new note in collection "
                + State.getSelectedCollection().getName());
        addNewNoteDialog.setScene(addNewNoteScene);
        addNewNoteDialog.show();
    }

    public void refreshNotesContainer() throws JsonParseException,
            JsonMappingException, IOException {
        notesService.loadNotesCurrentCollection(State.getSelectedCollection()
                .getId());
        notesTableView.setItems(notesService.notesCurrentCollection);

    }

    public void saveNoteChanges() throws JsonParseException,
            JsonMappingException, IOException {
        Note selectedNote = notesTableView.getSelectionModel()
                .getSelectedItem();
        selectedNote.setFront(noteFront.getText());
        selectedNote.setBack(noteBack.getText());
        notesService.updateNote(selectedNote);
        refreshNotesContainer();
    }

    public void deleteNote() throws JsonParseException, JsonMappingException,
            IOException {
        Note selectedNote = notesTableView.getSelectionModel()
                .getSelectedItem();
        notesService.deleteNote(selectedNote);
        refreshNotesContainer();
    }

}
