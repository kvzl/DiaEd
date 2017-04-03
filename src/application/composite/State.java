package application.composite;

import javafx.geometry.Point2D;

/**
 * Created by ucfan on 2017/3/28.
 */

public class State extends DiagramElement {
    public State() {
        super(new Point2D(150, 150));
    }

    public State(Point2D position) {
        super(position);
    }
}



