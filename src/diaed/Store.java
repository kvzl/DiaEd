package diaed;

import diaed.command.*;
import diaed.history.EditHistory;
import diaed.model.DiagramElement;
import diaed.model.State;
import diaed.model.StateDiagram;
import diaed.model.Transition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;


/**
 * Created by ucfan on 2017/3/31.
 */


/* 一個 Mediator 的概念 */

public class Store {
    private Root root;

    // 狀態圖 data
    private ObjectProperty<StateDiagram> diagram = new SimpleObjectProperty<>();

    // 記錄目前選取的元素
    private ObjectProperty<DiagramElement> selected = new SimpleObjectProperty<>();

    // 記錄目前正在編輯的元素
    private ObjectProperty<DiagramElement> editing = new SimpleObjectProperty<>();

    // 編輯紀錄
    // 用來 undo/redo
    private EditHistory histories = new EditHistory();



    /* getters 和 setters */

    public StateDiagram getDiagram() {
        return diagram.get();
    }

    public void setRoot(Root root) {
        this.root = root;
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
        saveHistory();
        this.editing.set(editing);
    }

    public ObjectProperty<DiagramElement> editingProperty() {
        return editing;
    }

    public ObjectProperty<DiagramElement> selectedProperty() {
        return this.selected;
    }



    // 繪製（將元件加入到畫布中）
    public void draw(Node node) {
        root.add(node);
    }

    // 將目前狀態存入編輯紀錄
    // 進行變更前呼叫此方法，之後可 redo
    public void saveHistory() {
        histories.invoke(new SaveHistoryCommand(this));
    }

    public void setDiagram(StateDiagram diagram) {
        // 清空畫面
        root.clear();
        root.setModel(diagram);
        this.diagram = root.modelProperty();
    }

    public void resetHistory() {
        histories.reset();
    }


    // 新建狀態圖
    public void newDiagram() {
        setDiagram(new StateDiagram());
        resetHistory();
    }

    // 讀檔（讀取預先定義的範例）
    public void loadDiagram() {
        setDiagram(getTemplateDiagram());
        resetHistory();
    }

    // 復原
    public void undo() {
        if (histories.undoable()) {
            histories.undo();
        }
    }

    // 還原
    public void redo() {
        if (histories.redoable()) {
            histories.redo();
        }
    }

    // 編輯選取的元素
    public void editElement() {
        histories.invoke(new EditElementCommand(this));
    }

    // 刪除選取的元素
    public void deleteElement() {
        histories.invoke(new DeleteElementCommand(this));
    }

    // 在畫面上新增 state
    public void addState() {
        histories.invoke(new AddStateCommand(this));
    }

    // 在畫面上新增 transition
    public void addTransition() {
        histories.invoke(new AddTransitionCommand(this));
    }


    // 範本資料
    public StateDiagram getTemplateDiagram() {
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

