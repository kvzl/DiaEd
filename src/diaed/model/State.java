package diaed.model;

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

    @Override
    public State clone() {
        State state = new State();
        state.setPositionX(getPositionX());
        state.setPositionY(getPositionY());
        state.setName(new String(getName()));
        return state;
    }

}



