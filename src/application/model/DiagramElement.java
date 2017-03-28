package application.model;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Created by ucfan on 2017/3/28.
 */
public abstract class DiagramElement {
    protected Node shape;
    private Point2D position;

    public DiagramElement() {
        buildEvents();
    }

    public DiagramElement(Point2D position) {
        this();
        this.position = position;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D p) {
        position = p;
    }

    abstract public void draw(Pane pane);

    protected void buildEvents() { };
}
