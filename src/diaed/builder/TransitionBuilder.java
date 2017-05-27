package diaed.builder;

import diaed.model.Transition;

/**
 * Created by ucfan on 2017/5/28.
 */
public class TransitionBuilder implements Builder<Transition> {
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
    public Builder setPosition(double X, double Y) {
        positionX = X;
        positionY = Y;
        return this;
    }

    @Override
    public Builder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public Builder setDestination(double X, double Y) {
        destinationX = X;
        destinationY = Y;
        return this;
    }
}
