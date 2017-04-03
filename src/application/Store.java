package application;

import application.composite.State;
import application.composite.Transition;
import application.viewModel.StateViewModel;
import application.viewModel.TransitionViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

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
        return canvas;
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
            StateViewModel viewModel = new StateViewModel(new State());
            viewModel.draw(this);
        });

        toolbar.setOnAddTranstion(event -> {
            TransitionViewModel viewModel = new TransitionViewModel(new Transition());
            viewModel.draw(this);
        });
    }

}

