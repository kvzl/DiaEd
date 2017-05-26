package diaed.util;

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


    /**
     * 綁定位移座標
     * @param node
     */
    public void bindToPoint(Node node) {
        translateX.addListener(((observable, oldValue, newValue) -> {
            node.setTranslateX((double)newValue);
        }));

        translateY.addListener(((observable, oldValue, newValue) -> {
            node.setTranslateY((double)newValue);
        }));
    }


    /**
     * 綁定位移座標
     * 註：通常是需要在拖曳時同步更新 model 才會用到，如果是釋放拖曳才要更新的話就不會用到這個方法
     * @param xProperty
     * @param yProperty
     */
    public void bindToPoint(DoubleProperty xProperty, DoubleProperty yProperty) {
        double initX = xProperty.get();
        double initY = yProperty.get();

        translateX.addListener(((observable, oldValue, newValue) -> {
            xProperty.set(initX + (double)newValue);
        }));

        translateY.addListener(((observable, oldValue, newValue) -> {
            yProperty.set(initY + (double)newValue);
        }));
    }

    public double getTranslateX() {
        return translateX.get();
    }

    public double getTranslateY() {
        return translateY.get();
    }

}
