package diaed.model;

import diaed.builder.AbstractBuilder;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;


/**
 * Created by ucfan on 2017/3/28.
 */
public class Transition extends DiagramElement {
    // 座標（尾端）
    private DoubleProperty destinationX = new SimpleDoubleProperty();
    private DoubleProperty destinationY = new SimpleDoubleProperty();

    public Transition() {
        // 預設座標
        setPositionX(150);
        setPositionY(150);
        setDestinationX(350);
        setDestinationY(150);
        setName("TRANSITION");
    }

    public Transition(Builder builder) {
        setPositionX(builder.getPositionX());
        setPositionY(builder.getPositionY());
        setDestinationX(builder.getDestinationX());
        setDestinationY(builder.getDestinationY());
        setName(builder.getName());
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


    @Override
    public Transition clone() {
        Transition transition = new Transition();
        transition.setPositionX(getPositionX());
        transition.setPositionY(getPositionY());
        transition.setDestinationX(getDestinationX());
        transition.setDestinationY(getDestinationY());
        transition.setName(new String(getName()));
        return transition;
    }


    public static class Builder implements AbstractBuilder<Transition> {
        private double positionX;
        private double positionY;
        private double destinationX;
        private double destinationY;
        private String name;

        public double getPositionX() { return positionX; }
        public double getPositionY() { return positionY; }
        public double getDestinationX() { return destinationX; }
        public double getDestinationY() { return destinationY; }
        public String getName() { return name; }

        @Override
        public Transition build() {
            return new Transition(this);
        }

        @Override
        public AbstractBuilder setPosition(double X, double Y) {
            positionX = X;
            positionY = Y;
            return this;
        }

        @Override
        public AbstractBuilder setName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public AbstractBuilder setDestination(double X, double Y) {
            destinationX = X;
            destinationY = Y;
            return this;
        }
    }
}

