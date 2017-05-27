package diaed.builder;

import diaed.model.State;

/**
* Created by ucfan on 2017/5/28.
*/
public class StateBuilder implements Builder<State> {
    private double positionX;
    private double positionY;
    private String name;

    public double getPositionX() { return positionX; }
    public double getPositionY() { return positionY; }
    public String getName() { return name; }

    @Override
    public State build() {
        return new State(this);
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
        // do nothing
        return this;
    }
}
