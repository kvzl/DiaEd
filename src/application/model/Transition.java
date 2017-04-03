package application.model;

import application.Store;
import application.viewModel.TransitionViewModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;


/**
 * Created by ucfan on 2017/3/28.
 */
public class Transition extends DiagramElement {
    private DoubleProperty destinationX = new SimpleDoubleProperty();
    private DoubleProperty destinationY = new SimpleDoubleProperty();

    public Transition() {
        setPositionX(150);
        setPositionY(150);
        setDestinationX(350);
        setDestinationY(150);
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
    public void draw(Store store) {
        if (viewModel == null) {
            viewModel = new TransitionViewModel(this);
        }
        viewModel.draw(store);
    }
}

