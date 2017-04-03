package application.history;

/**
 * Created by ucfan on 2017/4/4.
 */
public class Memento {
    DiagramState state;

    public DiagramState getState() {
        return state;
    }

    public void setState(DiagramState state) {
        this.state = state;
    }
}
