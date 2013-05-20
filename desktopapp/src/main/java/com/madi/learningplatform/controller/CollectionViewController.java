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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
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
import com.madi.learningplatform.exceptions.CollectionNotFoundException;
import com.madi.learningplatform.exceptions.NoteNotFoundException;
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
    protected TableColumn<Note, String> learnedColumn;
    @FXML
    protected TextArea noteFront;
    @FXML
    protected TextArea noteBack;
    @FXML
    protected VBox noteDetails;
    @FXML protected CheckBox learnedCheckbox;

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

        AnchorPane.setLeftAnchor(content, 10.0);
        AnchorPane.setRightAnchor(content, 10.0);
        notesScrollPane.setFitToWidth(true);

        this.parentController = parentController;
        this.collectionService = collectionService;
        this.notesService = notesService;

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
                                learnedCheckbox.setSelected(selectedNote.getLearned());
                            }
                        }
                    });

            frontColumn
                    .setCellValueFactory(new PropertyValueFactory<Note, String>(
                            "front"));
            dateAddedColumn
                    .setCellValueFactory(new PropertyValueFactory<Note, String>(
                            "dateAdded"));
            learnedColumn.setCellValueFactory(new PropertyValueFactory<Note, String>(
                    "learned"));
                
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
                    try
                    {
                    notesService.addNote(new Note(front, back),
                            State.getSelectedCollection());
                    }
                    catch(CollectionNotFoundException ex) {
                        State.getMainApp().showErrorDialog(ex.getMessage());
                    }
                    
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
        notesService.loadNotesInCollection(State.getSelectedCollection()
                .getId());
        notesTableView.setItems(notesService.notesCurrentCollection);

    }

    public void saveNoteChanges() throws JsonParseException,
            JsonMappingException, IOException {
        Note selectedNote = notesTableView.getSelectionModel()
                .getSelectedItem();
        selectedNote.setFront(noteFront.getText());
        selectedNote.setBack(noteBack.getText());
        try {
            notesService.updateNote(selectedNote);
        } catch (NoteNotFoundException e) {
            State.getMainApp().showErrorDialog(e.getMessage());
        }
        refreshNotesContainer();
    }
    
    public void noteLearnedHandler() {
        Note selectedNote = notesTableView.getSelectionModel()
                .getSelectedItem();
        selectedNote.setLearned(learnedCheckbox.isSelected());
    }

    public void deleteNote() throws JsonParseException, JsonMappingException,
            IOException {
        Note selectedNote = notesTableView.getSelectionModel()
                .getSelectedItem();
        try {
            notesService.deleteNote(selectedNote);
        } catch (NoteNotFoundException e) {
            State.getMainApp().showErrorDialog(e.getMessage());
        }
        refreshNotesContainer();
    }
    
    class BooleanCell extends TableCell<Note, Boolean> {
        private CheckBox checkBox;
        public BooleanCell() {
            checkBox = new CheckBox();
            checkBox.setDisable(true);
            checkBox.selectedProperty().addListener(new ChangeListener<Boolean> () {
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(isEditing())
                        commitEdit(newValue == null ? false : newValue);
                }
            });
            this.setGraphic(checkBox);
            this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            this.setEditable(true);
        }
        @Override
        public void startEdit() {
            super.startEdit();
            if (isEmpty()) {
                return;
            }
            checkBox.setDisable(false);
            checkBox.requestFocus();
        }
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            checkBox.setDisable(true);
        }
        public void commitEdit(Boolean value) {
            super.commitEdit(value);
            checkBox.setDisable(true);
        }
        @Override
        public void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!isEmpty()) {
                checkBox.setSelected(item);
            }
        }
    }

}
