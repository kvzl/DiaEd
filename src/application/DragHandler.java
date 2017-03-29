package application;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

/**
 * Created by ucfan on 2017/3/28.
 */

public class DragHandler {
    Point2D orgScene;
    Point2D orgTranslate;

    public Point2D getOrgTranslate() {
        return orgTranslate;
    }

    private EventHandler<MouseEvent> onPressed;
    private EventHandler<MouseEvent> onDragged;

    public EventHandler<MouseEvent> getOnPressed() {
        return onPressed;
    }
    public EventHandler<MouseEvent> getOnDragged() {
        return onDragged;
    }


    public DragHandler() {
        setOnPressed(null);
        setOnDragged(null);
    }

    public void setOnPressed(EventHandler<MouseEvent> onPressed) {
        EventHandler<MouseEvent> onPressedDefault = t -> {
            Shape source = (Shape) t.getSource();

            orgScene = new Point2D(
                    t.getSceneX(),
                    t.getSceneY()
            );
            orgTranslate = new Point2D(
                    ((Shape)(t.getSource())).getTranslateX(),
                    ((Shape)(t.getSource())).getTranslateY()
            );

            System.out.println("orgScene: " + orgScene);
            System.out.println("orgTranslate: " + orgTranslate);
        };

        if (onPressed == null) {
            this.onPressed = onPressedDefault;
        }
        else {
            this.onPressed = t -> {
                onPressedDefault.handle(t);
                onPressed.handle(t);
            };
        }
    }


    public void setOnDragged(EventHandler<MouseEvent> onDragged) {
        EventHandler<MouseEvent> onDraggedDefault = t -> {
            double offsetX = t.getSceneX() - orgScene.getX();
            double offsetY = t.getSceneY() - orgScene.getY();
            double newTranslateX = orgTranslate.getX() + offsetX;
            double newTranslateY = orgTranslate.getY() + offsetY;

            ((Shape)(t.getSource())).setTranslateX(newTranslateX);
            ((Shape)(t.getSource())).setTranslateY(newTranslateY);

            System.out.println("offset x = " + offsetX + ",  offset y = " + offsetY);

        };

        if (onDragged == null) {
            this.onDragged = onDraggedDefault;
        }
        else {
            this.onDragged = t -> {
                onDraggedDefault.handle(t);
                onDragged.handle(t);
            };
        }
    }
}
