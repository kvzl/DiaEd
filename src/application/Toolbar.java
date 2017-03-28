package application;

import application.model.State;
import application.model.Transition;
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
        addStateButton.setOnAction(event -> {
            System.out.println("add state");

            State state = new State();
            state.draw(canvas);

        });

        addTransitionButton.setOnAction(event -> {
            System.out.println("add transition");

            Transition trans = new Transition();
            trans.draw(canvas);
        });

    }

}
