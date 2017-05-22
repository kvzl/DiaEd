package diaed.history;

import java.util.Stack;

/**
 * Created by ucfan on 2017/4/3.
 */


/**
 * 負責處理 undo/redo（編輯紀錄）
 * 對應到 Memento Pattern 的 CareTaker
 * 以 Memento 為單位做處理
 */

public class EditHistory {
    private Stack<Memento> undoStack = new Stack<>();
    private Stack<Memento> redoStack = new Stack<>();


    // 判斷是否可 ctrl-z
    public boolean undoable() {
        return !undoStack.empty();
    }

    // 判斷是否可 ctrl-y
    public boolean redoable() {
        return !redoStack.empty();
    }


    // 新增一筆紀錄
    public void push(Memento current) {
        undoStack.push(current);

        if (!redoStack.empty()) {
            redoStack.clear();
        }
    }

    // ctrl-z
    public Memento undo(Memento current) {
        if (!undoable()) {
            return null;
        }
        Memento memento = undoStack.pop();
        redoStack.push(current);
        return memento;
    }

    // ctrl-y
    public Memento redo(Memento current) {
        if (!redoable()) {
            return null;
        }
        Memento memento = redoStack.pop();
        undoStack.push(current);
        return memento;
    }

    // 重置
    public void reset() {
        undoStack.clear();
        redoStack.clear();
    }
}
