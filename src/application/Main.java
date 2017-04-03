package application;

import application.composite.State;
import application.composite.StateDiagram;
import application.composite.Transition;
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

//		StateDiagram diagram = initData();
//
//        Iterator iter = diagram.iterator();
//
//        while (iter.hasNext()) {
//            DiagramElement element = (DiagramElement)iter.next();
//            element.draw(store);
//        }
	}

    public static void main(String[] args) {
        launch(args);
    }

    private StateDiagram initData() {
        State state1 = new State(new Point2D(100, 100));

        State state2 = new State(new Point2D(230, 230));

        Transition trans1 = new Transition(
                new Point2D(250, 250),
                new Point2D(170, 170)
        );

        StateDiagram diagram = new StateDiagram();
        diagram.add(state1);
        diagram.add(state2);
        diagram.add(trans1);

        return diagram;
    }
}
