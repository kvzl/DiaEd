package diaed.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;


/**
 * Created by ucfan on 2017/3/28.
 */
public abstract class DiagramElement {
    // 座標
    private DoubleProperty positionX = new SimpleDoubleProperty();
    private DoubleProperty positionY = new SimpleDoubleProperty();

    // 名稱
    private StringProperty name = new SimpleStringProperty("");

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

    abstract public SerializableElement serialize();

    abstract static class SerializableElement implements Serializable {
        abstract DiagramElement deserialize();
    };
}
