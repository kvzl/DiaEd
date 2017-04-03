package application;

import application.model.State;
import application.model.StateDiagram;
import application.model.Transition;
import application.view.Canvas;
import application.view.Toolbar;
import application.viewModel.StateViewModel;
import application.viewModel.TransitionViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

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

    public Canvas getCanvas() {
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
        toolbar.setOnNew(event -> {
            canvas.clear();
        });

        toolbar.setOnLoad(event -> {
            canvas.clear();
            StateDiagram diagram = initData();
            diagram.draw(this);
        });

        toolbar.setOnAddState(event -> {
            StateViewModel viewModel = new StateViewModel(new State());
            viewModel.draw(this);
        });

        toolbar.setOnAddTranstion(event -> {
            TransitionViewModel viewModel = new TransitionViewModel(new Transition());
            viewModel.draw(this);
        });
    }

    private StateDiagram initData() {
        State state1 = new State();
        state1.setPositionX(100);
        state1.setPositionY(100);

        State state2 = new State();
        state2.setPositionX(230);
        state2.setPositionY(230);

        Transition trans1 = new Transition();
        trans1.setPositionX(250);
        trans1.setPositionY(250);
        trans1.setDestinationX(170);
        trans1.setDestinationY(170);

        StateDiagram diagram = new StateDiagram();
        diagram.add(state1);
        diagram.add(state2);
        diagram.add(trans1);

        System.out.println(diagram.get(state1));
        System.out.println(diagram.get(state2));
        System.out.println(diagram.get(trans1));

        return diagram;
    }

}

