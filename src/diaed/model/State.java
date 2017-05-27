package diaed.model;


import diaed.builder.StateBuilder;

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

    public State(StateBuilder builder) {
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

}



