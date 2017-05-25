package diaed.view;

import diaed.util.DragHandler;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 * Created by ucfan on 2017/4/4.
 */
public class EditableText extends Group {
    // 長寬
    private DoubleProperty width = new SimpleDoubleProperty(110);
    private DoubleProperty height = new SimpleDoubleProperty(28);
    private Pane mask = new Pane();
    private TextField text = new TextField();
    private DragHandler dragHandler;

    public EditableText(double positionX, double positionY) {
        super();
        text.getStyleClass().add("state-text");
        text.setEditable(false);

        ChangeListener updater = (observable, oldValue, newValue) -> {
            text.setPrefWidth(getWidth());
            text.setPrefHeight(getHeight());
            relocate(
                    positionX - getWidth()/2,
                    positionY - getHeight()/2
            );

            mask.setPrefWidth(getWidth());
            mask.setPrefHeight(getHeight());
        };

        widthProperty().addListener(updater);
        heightProperty().addListener(updater);
        updater.changed(null, null, null);

        mask.getStyleClass().add("state-text-mask");

        this.getChildren().add(text);
        this.getChildren().add(mask);
    }


    public EditableText(DoubleProperty xProperty, DoubleProperty yProperty) {
        this(xProperty.get(), yProperty.get());

        dragHandler = new DragHandler(this);
        dragHandler.bindToPoint(this);
        dragHandler.bindToPoint(xProperty, yProperty);
        this.setOnMousePressed(dragHandler.getOnPressed());
        this.setOnMouseDragged(dragHandler.getOnDragged());
    }


    public DoubleProperty widthProperty() {
        return width;
    }

    public double getWidth() {
        return width.get();
    }

    public void setWidth(double width) {
        this.width.set(width);
    }

    public DoubleProperty heightProperty() {
        return height;
    }

    public double getHeight() {
        return height.get();
    }

    public void setHeight(double height) {
        this.height.set(height);
    }

    public DragHandler getDragHandler() {
        return dragHandler;
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public String getText() {
        return text.getText();
    }

    public void setEditable(boolean editable) {
        this.text.setEditable(editable);

        if (editable) {
            getStyleClass().add("editing");
            text.toFront();
            text.requestFocus();
        }
        else {
            getStyleClass().remove("editing");
            mask.toFront();
        }
    }

    public void bindText(StringProperty textProperty) {
        textProperty().bindBidirectional(textProperty);
    }


    public StringProperty textProperty() {
        return text.textProperty();
    }

    public void setOnKeyIn(EventHandler<KeyEvent> handler) {
        text.setOnKeyPressed(handler);
    }

}
