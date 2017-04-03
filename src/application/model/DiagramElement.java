package application.model;

import application.Store;
import application.viewModel.ViewModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;


/**
 * Created by ucfan on 2017/3/28.
 */
public abstract class DiagramElement {
    private DoubleProperty positionX = new SimpleDoubleProperty();
    private DoubleProperty positionY = new SimpleDoubleProperty();
    protected ViewModel viewModel;

    public double getPositionX() {
        return positionX.get();
    }

    public void setPositionX(double x){
        positionX.set(x);
    }

    public double getPositionY() {
        return positionY.get();
    }

    public void setPositionY(double y){
        positionY.set(y);
    }

    public DoubleProperty positionXProperty() {
        return positionX;
    }

    public DoubleProperty positionYProperty() {
        return positionY;
    }

    public ViewModel getViewModel() {
        return viewModel;
    }

    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    abstract public void draw(Store store);

    public DiagramElement clone() {
        return this.clone();
    };
}
