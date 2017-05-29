package diaed.model;


import diaed.builder.AbstractBuilder;


/**
 * Created by ucfan on 2017/3/28.
 */

public class State extends DiagramElement {
    public State() {
        // 預設座標
        setPositionX(150);
        setPositionY(150);
        setName("STATE");
    }

    public State(Builder builder) {
        setPositionX(builder.getPositionX());
        setPositionY(builder.getPositionY());
        setName(builder.getName());
    }


    @Override
    public State clone() {
        State state = new State();
        state.setPositionX(getPositionX());
        state.setPositionY(getPositionY());
        state.setName(new String(getName()));
        return state;
    }


    public static class Builder implements AbstractBuilder<State> {
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


    @Override
    public SerializableElement serialize() {
        return new SerializableElement(this);
    }

    public static class SerializableElement extends DiagramElement.SerializableElement {
        private double positionX;
        private double positionY;
        private String name;

        public SerializableElement(State element) {
            positionX = element.getPositionX();
            positionY = element.getPositionY();
            name = element.getName();
        }

        @Override
        public State deserialize() {
            State state = new State();
            state.setPositionX(positionX);
            state.setPositionY(positionY);
            state.setName(name);
            return state;
        }
    }
}



