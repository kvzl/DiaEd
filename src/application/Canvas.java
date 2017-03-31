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

    Store store;

    public Canvas(Store store) {
        this.store = store;

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

    @FXML
    private void initialize() {
        this.setOnMouseClicked(event -> {
            System.out.println(event.getSource());
        });
    }

    public AnchorPane getCanvas() {
        return this.canvas;
    }

}
