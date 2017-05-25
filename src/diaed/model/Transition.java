package diaed.model;

import diaed.Store;
import diaed.view.TransitionView;
import diaed.viewModel.TransitionViewModel;
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
}

