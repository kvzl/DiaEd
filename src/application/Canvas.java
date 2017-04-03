package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by ucfan on 2017/3/27.
 */
public class Canvas extends AnchorPane {
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
            store.setSelected(null);
        });
    }

}
