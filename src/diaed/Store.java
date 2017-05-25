package diaed;

import diaed.history.EditHistory;
import diaed.history.Memento;
import diaed.model.DiagramElement;
import diaed.model.State;
import diaed.model.StateDiagram;
import diaed.model.Transition;
import diaed.view.Canvas;
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

    // 記錄目前選取的元素
    private ObjectProperty<DiagramElement> selected = new SimpleObjectProperty<>();

    // 記錄目前正在編輯的元素
    private ObjectProperty<DiagramElement> editing = new SimpleObjectProperty<>();


    // 狀態圖 data
    private StateDiagram diagram;
    private StateDiagramViewModel vm;

    // 編輯紀錄
    // 用來 undo/redo
    private EditHistory histories = new EditHistory();


    public Store() {
        canvas = new Canvas(this);
        toolbar = new Toolbar(this);

        setDiagram(new StateDiagram());
    }


    public void setDiagram(StateDiagram diagram) {
        canvas.clear();
        // 建立 View Model
        this.vm = new StateDiagramViewModel(this, diagram);

        // 監聽 model，當 model 變動時重新繪製
        this.diagram = vm.getModel();
        this.diagram.addListener(c -> redraw());
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

    public DiagramElement getEditing() {
        return this.editing.get();
    }

    public void setEditing(DiagramElement editing) {
        this.editing.set(editing);
    }

    public ObjectProperty<DiagramElement> editingProperty() {
        return editing;
    }



    // 監看目前選取的元件
    public ObjectProperty<DiagramElement> selectedProperty() {
        return this.selected;
    }


    // 繪製（將元件加入到畫布中）
    public void draw(Node node) {
        canvas.getChildren().add(node);
    }


    // 重新繪製畫面
    // NOTE: 可優化成只重新繪製有差異的部分
    public void redraw() {
        canvas.clear();
        vm.initialize();
    }


    // 將目前狀態存入編輯紀錄
    // 進行變更前呼叫此方法，之後可 redo
    public void saveHistory() {
        histories.push(diagram.save());
    }


    // 新建狀態圖
    public void newDiagram() {
        setDiagram(new StateDiagram());
        histories.reset();
    }

    // 讀檔（讀取預先定義的範例）
    public void loadDiagram() {
        setDiagram(getTemplateDiagram());
        histories.reset();
    }

    // 復原
    public void undo() {
        if (histories.undoable()) {
            Memento currentState = diagram.save();
            Memento newState = histories.undo(currentState);
            diagram.restore(newState);
        }
    }

    // 還原
    public void redo() {
        if (histories.redoable()) {
            Memento currentState = diagram.save();
            Memento newState = histories.redo(currentState);
            diagram.restore(newState);
        }
    }

    // 編輯選取的元素
    public void editElement() {
        setEditing(getSelected());
    }

    // 刪除選取的元素
    public void deleteElement() {
        saveHistory();
        diagram.remove(getSelected());
        setSelected(null);
    }

    // 在畫面上新增 state
    public void addState() {
        saveHistory();
        State state = new State();
        new StateViewModel(this, state);
        diagram.add(state);
    }

    // 在畫面上新增 transition
    public void addTransition() {
        saveHistory();
        Transition transition = new Transition();
        new TransitionViewModel(this, transition);
        diagram.add(transition);
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

