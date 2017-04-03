package application;

import application.model.State;
import application.model.Transition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Shape;

/**
 * Created by ucfan on 2017/3/31.
 */
public class Store {
    private ObjectProperty<Node> selected = new SimpleObjectProperty<>();
    private Canvas canvas;
    private Toolbar toolbar;

    public Store() {
        canvas = new Canvas(this);
        toolbar = new Toolbar(this);
        bindToolbarActions();
    }

    public AnchorPane getCanvas() {
        return canvas.getCanvas();
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setSelected(Node selected) {
        this.selected.set(selected);
    }

    public Node getSelected() {
        return this.selected.get();
    }

    public ObjectProperty<Node> selectedProperty() {
        return this.selected;
    }

    private void bindToolbarActions() {
        toolbar.setOnAddState(event -> {
            State state = new State();
            state.draw(this);
        });

        toolbar.setOnAddTranstion(event -> {
            Transition trans = new Transition();
            trans.draw(this);
        });
    }

}

