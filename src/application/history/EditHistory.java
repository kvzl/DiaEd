package application.history;

import java.util.Stack;

/**
 * Created by ucfan on 2017/4/3.
 */
public class EditHistory {
    private Stack<Memento> undoStack = new Stack<>();
    private Stack<Memento> redoStack = new Stack<>();


    public boolean undoable() {
        return !undoStack.empty();
    }

    public boolean redoable() {
        return !redoStack.empty();
    }

    public void push(Memento current) {
        undoStack.push(current);

        if (!redoStack.empty()) {
            redoStack.clear();
        }
    }

    public Memento undo(Memento current) {
        if (!undoable()) {
            return null;
        }
        Memento memento = undoStack.pop();
        redoStack.push(current);
        return memento;
    }

    public Memento redo(Memento current) {
        if (!redoable()) {
            return null;
        }
        Memento memento = redoStack.pop();
        undoStack.push(current);
        return memento;
    }

    public void reset() {
        undoStack.clear();
        redoStack.clear();
    }
}
