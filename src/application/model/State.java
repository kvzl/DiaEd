package application.model;

import application.DragHandler;
import application.Store;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

/**
 * Created by ucfan on 2017/3/28.
 */

public class State extends DiagramElement {
    private final double RADIUS = 50;

    DragHandler dragHandler;


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

        dragHandler = new DragHandler(shape);

        bindListeners(store);

        canvas.getChildren().add(shape);
    }


    private void bindListeners(Store store) {
        dragHandler.bindToPoint(shape, positionXProperty(), positionYProperty());

        shape.setOnMousePressed(dragHandler.getOnPressed());
        shape.setOnMouseDragged(dragHandler.getOnDragged());

        shape.setOnMouseClicked(event -> {
            store.setSelected(shape);
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

    }

}



