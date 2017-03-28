package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by ucfan on 2017/3/27.
 */
public class Canvas extends AnchorPane {
    @FXML
    private AnchorPane canvas;

    public Canvas() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/Canvas.fxml")
        );

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public AnchorPane getCanvas() {
        return this.canvas;
    }

}
