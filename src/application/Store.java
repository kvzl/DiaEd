package application;

import application.history.EditHistory;
import application.history.Memento;
import application.model.DiagramElement;
import application.model.State;
import application.model.StateDiagram;
import application.model.Transition;
import application.view.Canvas;
import application.view.Toolbar;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;


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

    // 狀態圖 data
    private StateDiagram diagram = new StateDiagram();

    // 編輯紀錄
    // 用來 undo/redo
    private EditHistory histories = new EditHistory();


    public Store() {
        canvas = new Canvas(this);
        toolbar = new Toolbar(this);

        // 綁定工具列按鈕的各種 observer
        bindToolbarActions();
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


    // 重繪制
    private void redraw() {
        canvas.clear();
        diagram.draw(this);
    }


    // 將目前狀態存入編輯紀錄
    // 進行變更前呼叫此方法，之後可 redo
    public void saveHistory() {
        histories.push(diagram.save());
    }


    // 綁定工具列按鈕的各種 observer
    public void bindToolbarActions() {
        // 開新檔案（就是清空啦）
        toolbar.setOnNew(event -> {
            diagram = new StateDiagram();
            redraw();
            histories.reset();
        });

        // 讀檔（讀取預先定義的範例）
        toolbar.setOnLoad(event -> {
            diagram = initData();
            redraw();
            histories.reset();
        });

        // 復原
        toolbar.setOnUndo(event -> {
            if (histories.undoable()) {
                Memento currentState = diagram.save();
                Memento newState = histories.undo(currentState);
                diagram.restore(newState);
                redraw();
            }
        });

        // 還原
        toolbar.setOnRedo(event -> {
            if (histories.redoable()) {
                Memento currentState = diagram.save();
                Memento newState = histories.redo(currentState);
                diagram.restore(newState);
                redraw();
            }
        });

        // 刪除
        toolbar.setOnDelete(event -> {
            saveHistory();
            diagram.remove(selected.get());
            setSelected(null);
            redraw();
        });

        // 在畫面上新增 state
        toolbar.setOnAddState(event -> {
            saveHistory();
            State state = new State();
            state.draw(this);
            diagram.add(state);
        });

        // 在畫面上新增 transition
        toolbar.setOnAddTranstion(event -> {
            saveHistory();
            Transition transition = new Transition();
            transition.draw(this);
            diagram.add(transition);
        });
    }


    // 範本資料
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

