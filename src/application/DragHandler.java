package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

/**
 * Created by ucfan on 2017/3/28.
 */


/* NOTE: Favor composition over inheritance!! */

public class DragHandler {
    private double oldSceneX;
    private double oldSceneY;
    private double oldTranslateX;
    private double oldTranslateY;

    private DoubleProperty newTranslateX = new SimpleDoubleProperty();
    private DoubleProperty newTranslateY = new SimpleDoubleProperty();

    private EventHandler<MouseEvent> onPressed = t -> {
        oldSceneX = t.getSceneX();
        oldSceneY = t.getSceneY();
        oldTranslateX = ((Shape)t.getSource()).getTranslateX();
        oldTranslateY = ((Shape)t.getSource()).getTranslateY();
    };

    private EventHandler<MouseEvent> onDragged = t -> {
        double offsetX = t.getSceneX() - oldSceneX;
        double offsetY = t.getSceneY() - oldSceneY;
        newTranslateX.set(oldTranslateX + offsetX);
        newTranslateY.set(oldTranslateY + offsetY);

        ((Shape)t.getSource()).setTranslateX(newTranslateX.get());
        ((Shape)t.getSource()).setTranslateY(newTranslateY.get());
    };

    public EventHandler<MouseEvent> getOnPressed() {
        return onPressed;
    }

    public EventHandler<MouseEvent> getOnDragged() {
        return onDragged;
    }

}
