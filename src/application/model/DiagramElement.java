package application.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Created by ucfan on 2017/3/28.
 */
public abstract class DiagramElement {
    protected Node shape;

    private DoubleProperty positionX;
    private DoubleProperty positionY;

    public DiagramElement() {
    }

    public DiagramElement(Point2D position) {
        positionX = new SimpleDoubleProperty(position.getX());
        positionY= new SimpleDoubleProperty(position.getY());
    }

    public double getPositionX() {
        return positionX.get();
    }

    public void setPositionX(double x){
        positionX.set(x);
    }

    public double getPositionY() {
        return positionY.get();
    }

    public void setPositionY(double y){
        positionY.set(y);
    }


    public DoubleProperty positionXProperty() {
        return positionX;
    }

    public DoubleProperty positionYProperty() {
        return positionY;
    }

    abstract public void draw(Pane pane);
}
