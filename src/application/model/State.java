package application.model;

import application.DragHandler;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

/**
 * Created by ucfan on 2017/3/28.
 */
public class State extends DiagramElement {

    DragHandler dragHandler = new DragHandler();


    public State() {
        super(new Point2D(150, 150));
    }

    public State(Point2D position) {
        super(position);
    }

    @Override
    public void draw(Pane canvas) {
        Point2D position = getPosition();
        shape = new Circle(position.getX(), position.getY(), 50);

        shape.getStyleClass().add("state-circle");

        shape.setOnMousePressed(dragHandler.getOnPressed());
        shape.setOnMouseDragged(dragHandler.getOnDragged());

        canvas.getChildren().add(shape);


        System.out.println("draw state");
    }



}


