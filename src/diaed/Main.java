package diaed;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		// Mediator
		Store store = new Store();

        Root vm = new Root(store, primaryStage);
        vm.initialize();
	}

    public static void main(String[] args) {
        launch(args);
    }
}
