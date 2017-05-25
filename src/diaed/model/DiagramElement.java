package diaed.model;

import diaed.Store;
import diaed.viewModel.ViewModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Created by ucfan on 2017/3/28.
 */
public abstract class DiagramElement {
    // 座標
    private DoubleProperty positionX = new SimpleDoubleProperty();
    private DoubleProperty positionY = new SimpleDoubleProperty();

    // 名稱
    private StringProperty name = new SimpleStringProperty("");

    // View Model 負責處理繪圖、事件綁定、資料綁定
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

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public DiagramElement clone() {
        return this.clone();
    };
}
