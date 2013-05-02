package com.madi.learningplatform;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class NotePreview extends StackPane {
    private Button deleteButton;
    private VBox contentBox;
    
    public NotePreview(String title) {
        this.deleteButton = new Button("Delete note");
        this.contentBox = new VBox();
        
        Label titleLabel = new Label();
        titleLabel.setText(title);

        contentBox.getChildren().add(titleLabel);
        contentBox.getChildren().add(deleteButton);
        
        ImageView background = new ImageView("/images/note.jpg");
        background.setFitHeight(150);
        background.setFitWidth(150);
        
        this.getChildren().add(background);
        this.getChildren().add(contentBox);
        
    }
}
