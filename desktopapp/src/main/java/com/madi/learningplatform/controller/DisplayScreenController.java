package com.madi.learningplatform.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.madi.learningplatform.Collection;
import com.madi.learningplatform.MainApp;
import com.madi.learningplatform.Note;
import com.madi.learningplatform.State;
import com.madi.learningplatform.exceptions.CollectionDuplicateException;
import com.madi.learningplatform.service.CollectionService;
import com.madi.learningplatform.service.NoteService;


public class DisplayScreenController implements Initializable {
    private static final Logger log = LoggerFactory
            .getLogger(DisplayScreenController.class);
    
    private StudyViewController studyView;
    private CollectionViewController collectionView;
    private final ContextMenu contextMenu = new ContextMenu();
    private CollectionService collectionService;
    private NoteService notesService;
    
    @FXML
    private Label usernameLabel;
    @FXML
    private TextField addCollectionTextField;
    @FXML 
    private BorderPane borderPane;
    @FXML
    private AnchorPane currentCollectionContent;
    @FXML
    private VBox collectionsVBox;

    public void initialize(URL arg0, ResourceBundle arg1) {
        usernameLabel.setText(State.getUsername());
        collectionService = new CollectionService();
        notesService = new NoteService();
        
        collectionService.collections.addListener(new ListChangeListener<Collection>() {
            public void onChanged(ListChangeListener.Change change) {
                refreshCollectionList();
            }
        });
        
        notesService.notesCurrentCollection.addListener(new ListChangeListener<Note>() {
            public void onChanged(ListChangeListener.Change change) {
                refreshCollectionList();
            }
        });
        
        notesService.unlearnedNotesCurrentCollection.addListener(new ListChangeListener<Note>() {
            public void onChanged(ListChangeListener.Change change) {
                refreshCollectionList();
            }
        });
        
        borderPane.setCenter(null);
        createCollectionsContextMenu();
        refreshCollectionList();
    }

    private void createCollectionsContextMenu() {
        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                deleteSelectedCollection();
            }
        });

        MenuItem renameItem = new MenuItem("Rename");
        renameItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                renameSelectedCollection();
            }
        });
        
        MenuItem studyItem = new MenuItem("Study ");
        studyItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                switchToStudyView();
            }
        });

        contextMenu.getItems().addAll(deleteItem);
        contextMenu.getItems().addAll(renameItem);
        contextMenu.getItems().addAll(studyItem);
    }

    protected void switchToStudyView() {
        if(!collectionService.isSelectedCollectionRelevantForStudy())
        {
            State.getMainApp().showErrorDialog("There are no unlearned notes in this collection.");
            return;
        }
        
        studyView = new StudyViewController(collectionService, notesService);
        borderPane.setCenter(studyView);
    }
    
    protected void switchToCollectionView() {
        collectionView = new CollectionViewController(this, collectionService, notesService);
        borderPane.setCenter(collectionView);
    }
    
    protected void refreshCollectionList() {
        collectionsVBox.getChildren().clear();
        Iterator<Collection> it = collectionService.collections.iterator();
        while (it.hasNext()) {
            final Collection collection = it.next();

            HBox collectionRow = new HBox();
            collectionRow.getStyleClass().add("left-menu-row");
            collectionRow.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mouseEvent) {
                        setCurrentCollection(collection);
                        switchToCollectionView();
                    }
            });
            
            ImageView icon = new ImageView();
            icon.setImage(new Image("/images/1366248963_Notes.png"));
            icon.setFitHeight(40);
            icon.setFitWidth(40);
            VBox labelBox = new VBox();

            Label collectionLabel = new Label(collection.getName() + " ("
                    + notesService.countNotesInCollection(collection.getId()) + ")");
            collectionLabel.getStyleClass().add("left-menu-note-label");
            collectionLabel.setContextMenu(contextMenu);
            Label unlearnedLabel = new Label("[" + notesService.countUnlearnedNotesInCollection(collection.getId())+ " unlearned ]");
            unlearnedLabel.getStyleClass().add("left-menu-note-label");
            
            labelBox.setAlignment(Pos.CENTER);
            
            labelBox.getChildren().add(collectionLabel);
            labelBox.getChildren().add(unlearnedLabel);

            collectionRow.getChildren().add(icon);
            collectionRow.getChildren().add(labelBox);

            collectionsVBox.getChildren().add(collectionRow);
        }
    }

    public void logout() {
        State.getMainApp().setScene(MainApp.LOGIN);
    }

    public void addCollectionHandler() {
        String name = addCollectionTextField.getText();
        if (!name.isEmpty())
        {
            try
            {
                collectionService.addCollection(new Collection(name));
                addCollectionTextField.setText("");
            }
            catch(CollectionDuplicateException ex)
            {
                State.getMainApp().showErrorDialog(ex.getMessage());                
            }
        }
    }

    public void deleteSelectedCollection() {
        if ("".equals(State.getSelectedCollection())) {
            log.error("No collection selected.");
            return;
        }
        log.info("Deleted collection "
                + State.getSelectedCollection().getName());
        collectionService.deleteCollection(State.getSelectedCollection()
                .getId());
        currentCollectionContent.setVisible(false);
        setCurrentCollection(null);
    }

    public void renameSelectedCollection() {
        if (State.getSelectedCollection() == null) {
            log.error("No collection selected.");
            return;
        }

        final Stage renameDialog = new Stage();
        renameDialog.initModality(Modality.WINDOW_MODAL);

        final TextField renameCollectionField = new TextField();
        renameCollectionField.setText(State.getSelectedCollection().getName());
        final Scene renameCollectionScene;

        final EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    collectionService.renameCollection(State
                            .getSelectedCollection().getId(),
                            renameCollectionField.getText());
                    keyEvent.consume();
                    refreshCollectionList();
                    renameDialog.close();
                }
            }
        };

        renameCollectionField.setOnKeyReleased(keyEventHandler);

        renameCollectionScene = new Scene(VBoxBuilder.create()
                .children(renameCollectionField).alignment(Pos.CENTER)
                .padding(new Insets(10)).build());

        renameDialog.setTitle("Rename collection "
                + State.getSelectedCollection().getName());
        renameDialog.setWidth(300);
        renameDialog.setScene(renameCollectionScene);
        renameDialog.show();
    }

    private void setCurrentCollection(Collection collection) {
        if (collection == null) {
            State.getMainApp().setDefaultStageTitle();
            State.setSelectedCollection(null);
            return;
        }

        State.getMainApp().setStageTitle(collection.getName());
        State.setSelectedCollection(collection);

        try {
            notesService.loadNotesInCollection(collection.getId());
        } catch (JsonParseException e) {
            log.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
