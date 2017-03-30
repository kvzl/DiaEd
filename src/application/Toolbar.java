package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by ucfan on 2017/3/27.
 */
public class Toolbar extends ToolBar {
    @FXML
    Button newButton;

    @FXML
    Button saveButton;

    @FXML
    Button undoButton;

    @FXML
    Button redoButton;

    @FXML
    Button addStateButton;

    @FXML
    Button addTransitionButton;


    private AnchorPane canvas;


    public Toolbar(AnchorPane canvas) {
        this.canvas = canvas;

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/Toolbar.fxml")
        );

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }


    @FXML
	private void initialize() {
    }

    public void setOnAddState(EventHandler<ActionEvent> event) {
        addStateButton.setOnAction(event);
    }

    public void setOnAddTranstion(EventHandler<ActionEvent> event) {
        addTransitionButton.setOnAction(event);
    }

}
