package application.history;

import java.util.Stack;

/**
 * Created by ucfan on 2017/4/3.
 */
public class EditHistory {
    private Stack<DiagramState> histories = new Stack<>();
    private Stack<DiagramState> redoStack = new Stack<>();


    public boolean undoable() {
        return !histories.empty();
    }

    public boolean redoable() {
        return !redoStack.empty();
    }

    public void push(DiagramState state) {
        histories.push(state);

        if (!redoStack.empty()) {
            redoStack.clear();
        }
    }

    public DiagramState undo(DiagramState current) {
        if (!undoable()) {
            return null;
        }
        DiagramState state = histories.pop();
        redoStack.push(current);
        return state;
    }

    public DiagramState redo(DiagramState current) {
        if (!redoable()) {
            return null;
        }
        DiagramState state = redoStack.pop();
        histories.push(current);
        return state;
    }

    public void reset() {
        histories.clear();
        redoStack.clear();
    }
}
