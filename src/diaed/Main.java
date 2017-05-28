package diaed;

import diaed.scene.EditorScene;
import diaed.scene.StartupScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("DiaEd");
        primaryStage.setResizable(false);

        Store store = new Store(primaryStage);
        store.gotoStartup();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
