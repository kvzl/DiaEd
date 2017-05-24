package diaed;

import diaed.history.EditHistory;
import diaed.history.Memento;
import diaed.model.DiagramElement;
import diaed.model.State;
import diaed.model.StateDiagram;
import diaed.model.Transition;
import diaed.view.Canvas;
import diaed.view.StateView;
import diaed.view.Toolbar;
import diaed.viewModel.StateDiagramViewModel;
import diaed.viewModel.StateViewModel;
import diaed.viewModel.TransitionViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;


/**
 * Created by ucfan on 2017/3/31.
 */


/* 一個 Mediator 的概念 */

public class Store {
    // 畫布
    private Canvas canvas;

    // 工具列
    private Toolbar toolbar;

    // 記錄目前選取的元件
    private ObjectProperty<DiagramElement> selected = new SimpleObjectProperty<>();

    private ObjectProperty<DiagramElement> editing = new SimpleObjectProperty<>();

    public DiagramElement getEditing() {
        return this.editing.get();
    }

    public void setEditing(DiagramElement editing) {
        this.editing.set(editing);
    }

    public ObjectProperty<DiagramElement> editingProperty() {
        return editing;
    }


    // 狀態圖 data
    private ObjectProperty<StateDiagram> diagram;
    private StateDiagramViewModel vm;

    // 編輯紀錄
    // 用來 undo/redo
    private EditHistory histories = new EditHistory();


    public Store() {
        canvas = new Canvas(this);
        toolbar = new Toolbar(this);
        vm = new StateDiagramViewModel(this, new StateDiagram());
        diagram = vm.modelProperty();
    }

    /* getters 和 setters */

    public Canvas getCanvas() {
        return canvas;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setSelected(DiagramElement selected) {
        this.selected.set(selected);
    }

    public DiagramElement getSelected() {
        return this.selected.get();
    }


    // 監看目前選取的元件
    public ObjectProperty<DiagramElement> selectedProperty() {
        return this.selected;
    }


    // 繪製（將元件加入到畫布中）
    public void draw(Node node) {
        canvas.getChildren().add(node);
    }

    // 重繪制
    public void redraw() {
        canvas.clear();
        vm.initialize();
    }


    // 將目前狀態存入編輯紀錄
    // 進行變更前呼叫此方法，之後可 redo
    public void saveHistory() {
        histories.push(diagram.get().save());
    }


    public void newDiagram() {
        vm.setModel(new StateDiagram());
        redraw();
        histories.reset();
    }

    public void loadDiagram() {
        vm.setModel(getTemplateDiagram());
        redraw();
        histories.reset();
    }

    public void undo() {
        if (histories.undoable()) {
            Memento currentState = diagram.get().save();
            Memento newState = histories.undo(currentState);
            diagram.get().restore(newState);
            redraw();
        }
    }

    public void redo() {
        if (histories.redoable()) {
            Memento currentState = diagram.get().save();
            Memento newState = histories.redo(currentState);
            diagram.get().restore(newState);
            redraw();
        }
    }


    public void editElement() {
        setEditing(getSelected());
    }

    public void deleteElement() {
        saveHistory();
        diagram.get().remove(getSelected());
        setSelected(null);
        redraw();
    }


    public void addState() {
        saveHistory();
        State state = new State();
        StateViewModel vm = new StateViewModel(this, state);
        diagram.get().add(state);
   }

    public void addTransition() {
        saveHistory();
        Transition transition = new Transition();
        TransitionViewModel vm = new TransitionViewModel(this, transition);
        diagram.get().add(transition);
    }



    // 範本資料
    private StateDiagram getTemplateDiagram() {
        State state1 = new State();
        state1.setPositionX(200);
        state1.setPositionY(300);
        state1.setName("state1");

        State state2 = new State();
        state2.setPositionX(600);
        state2.setPositionY(300);
        state2.setName("state2");

        Transition trans1 = new Transition();
        trans1.setPositionX(260);
        trans1.setPositionY(300);
        trans1.setDestinationX(540);
        trans1.setDestinationY(300);
        trans1.setName("trans2");

        StateDiagram diagram = new StateDiagram();
        diagram.add(state1);
        diagram.add(state2);
        diagram.add(trans1);

        return diagram;
    }
}

