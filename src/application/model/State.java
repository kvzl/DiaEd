package application.model;

import application.DragHandler;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

/**
 * Created by ucfan on 2017/3/28.
 */

public class State extends DiagramElement {
    private final double RADIUS = 50;

    DragHandler dragHandler = new DragHandler();


    public State() {
        super(new Point2D(150, 150));
    }

    public State(Point2D position) {
        super(position);
    }

    @Override
    public void draw(Pane canvas) {
        shape = new Circle(getPositionX(), getPositionY(), RADIUS);
        shape.getStyleClass().add("state-circle");

        shape.setOnMousePressed(dragHandler.getOnPressed());
        shape.setOnMouseDragged(event -> {
            setPositionX(event.getSceneX());
            setPositionY(event.getSceneY() - 40);
            dragHandler.getOnDragged().handle(event);
        });

        canvas.getChildren().add(shape);
    }

}



