package application;

import application.model.State;
import application.model.StateDiagram;
import application.model.Transition;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {

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

		Canvas canvas = new Canvas();
		Toolbar toolbar = new Toolbar(canvas.getCanvas());

		toolbar.setOnAddState(event -> {
            System.out.println("add state");

            State state = new State();
            state.draw(canvas);

        });

		toolbar.setOnAddTranstion(event -> {
            System.out.println("add transition");

            Transition trans = new Transition();
            trans.draw(canvas);
        });

        root.setCenter(canvas);
		root.setTop(toolbar);

//        StateDiagram diagram = initData();
//        diagram.forEach(element -> element.draw(canvas));

	}

	public static void main(String[] args) {
		launch(args);
	}
}
