package application.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;


/**
 * Created by ucfan on 2017/3/28.
 */
public class Transition extends DiagramElement {
    private DoubleProperty destinationX;
    private DoubleProperty destinationY;

    public Transition() {
        super(new Point2D(150, 150));
        destinationX = new SimpleDoubleProperty(350);
        destinationY = new SimpleDoubleProperty(150);
    }

    public Transition(Point2D position, Point2D destination) {
        super(position);
        destinationX = new SimpleDoubleProperty(destination.getX());
        destinationY = new SimpleDoubleProperty(destination.getY());
    }

    public double getDestinationX() {
        return destinationX.get();
    }

    public void setDestinationX(double x) {
        destinationX.set(x);
    }

    public double getDestinationY() {
        return destinationY.get();
    }

    public void setDestinationY(double y) {
        destinationY.set(y);
    }

    public DoubleProperty destinationXProperty() {
        return destinationX;
    }

    public DoubleProperty destinationYProperty() {
        return destinationY;
    }

}





