package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * Created by ucfan on 2017/3/28.
 */

/* NOTE: Favor composition over inheritance!! */

public class DragHandler {
    private double oldSceneX;
    private double oldSceneY;
    private double oldTranslateX;
    private double oldTranslateY;

    private DoubleProperty translateX;
    private DoubleProperty translateY;

    private Node source;

    private EventHandler<MouseEvent> onPressed = t -> {
        oldSceneX = t.getSceneX();
        oldSceneY = t.getSceneY();

        if (source == null) {
            oldTranslateX = ((Node)t.getSource()).getTranslateX();
            oldTranslateY = ((Node)t.getSource()).getTranslateY();
        }
        else {
            oldTranslateX = source.getTranslateX();
            oldTranslateY = source.getTranslateY();
        }
    };

    private EventHandler<MouseEvent> onDragged = t -> {
        double offsetX = t.getSceneX() - oldSceneX;
        double offsetY = t.getSceneY() - oldSceneY;
        translateX.set(oldTranslateX + offsetX);
        translateY.set(oldTranslateY + offsetY);
    };


    public DragHandler(Node source) {
        this.translateX = new SimpleDoubleProperty(0);
        this.translateY = new SimpleDoubleProperty(0);
        this.source = source;
    }

    public EventHandler<MouseEvent> getOnPressed() {
        return onPressed;
    }

    public EventHandler<MouseEvent> getOnDragged() {
        return onDragged;
    }


    public void bindToPoint(Node node) {
        translateX.addListener(((observable, oldValue, newValue) -> {
            node.setTranslateX((double)newValue);
        }));

        translateY.addListener(((observable, oldValue, newValue) -> {
            node.setTranslateY((double)newValue);
        }));
    }


    public void bindToPoint(Node node, DoubleProperty xProperty, DoubleProperty yProperty) {
        double initX = xProperty.get();
        double initY = yProperty.get();

        translateX.addListener(((observable, oldValue, newValue) -> {
            xProperty.set(initX + (double)newValue);
            node.setTranslateX((double)newValue);
        }));

        translateY.addListener(((observable, oldValue, newValue) -> {
            yProperty.set(initY + (double)newValue);
            node.setTranslateY((double)newValue);
        }));
    }

}
