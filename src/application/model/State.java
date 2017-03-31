package application.model;

import application.DragHandler;
import application.Store;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

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
    public void draw(Store store) {
        AnchorPane canvas = store.getCanvas();

        shape = new Circle(getPositionX(), getPositionY(), RADIUS);
        shape.getStyleClass().add("state-circle");

        shape.setOnMousePressed(dragHandler.getOnPressed());
        shape.setOnMouseDragged(event -> {
            setPositionX(event.getSceneX());
            setPositionY(event.getSceneY() - 40);
            dragHandler.getOnDragged().handle(event);
        });

        shape.setOnMouseClicked(event -> {
            store.setSelected((Shape)shape);
            event.consume();
        });

        store.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == shape) {
                shape.getStyleClass().add("selected");
            }
            else {
                shape.getStyleClass().remove("selected");
            }
        });

        canvas.getChildren().add(shape);
    }

}



