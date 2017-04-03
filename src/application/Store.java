package application;

import application.history.DiagramState;
import application.history.EditHistory;
import application.model.State;
import application.model.StateDiagram;
import application.model.Transition;
import application.view.Canvas;
import application.view.Toolbar;
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
    private StateDiagram diagram = new StateDiagram();
    private EditHistory histories = new EditHistory();

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


    // 重繪制
    private void redraw() {
        canvas.clear();
        diagram.draw(this);
    }

    public void saveHistory() {
        histories.push(diagram.save());
    }

    private void bindToolbarActions() {
        toolbar.setOnNew(event -> {
            diagram = new StateDiagram();
            redraw();
            histories.reset();
        });

        toolbar.setOnLoad(event -> {
            diagram = initData();
            redraw();
            histories.reset();
        });

        toolbar.setOnUndo(event -> {
            if (histories.undoable()) {
                DiagramState currentState = diagram.save();
                diagram.restore(histories.undo(currentState));
                redraw();
            }
        });

        toolbar.setOnRedo(event -> {
            if (histories.redoable()) {
                DiagramState currentState = diagram.save();
                diagram.restore(histories.redo(currentState));
                redraw();
            }
        });

        toolbar.setOnAddState(event -> {
            saveHistory();
            State state = new State();
            state.draw(this);
            diagram.add(state);
        });

        toolbar.setOnAddTranstion(event -> {
            saveHistory();
            Transition transition = new Transition();
            transition.draw(this);
            diagram.add(transition);
        });
    }


    // 預設 data
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

        return diagram;
    }
}

