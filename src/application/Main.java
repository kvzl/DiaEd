package application;

import application.model.*;
import application.viewModel.StateDiagramViewModel;
import application.viewModel.StateViewModel;
import application.viewModel.ViewModel;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();

		try {
			Scene scene = new Scene(root,840,680);
			scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}

		Store store = new Store();

        root.setCenter(store.getCanvas());
		root.setTop(store.getToolbar());
	}

    public static void main(String[] args) {
        launch(args);
    }


}
