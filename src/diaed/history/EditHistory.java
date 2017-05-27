package diaed.history;

import diaed.command.Command;

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
    // 可復原的紀錄
    private Stack<Command> undoStack = new Stack<>();

    // 可重做的紀錄
    private Stack<Command> redoStack = new Stack<>();


    // 判斷是否可復原
    public boolean undoable() {
        return !undoStack.empty();
    }

    // 判斷是否可重做
    public boolean redoable() {
        return !redoStack.empty();
    }


    public void invoke(Command command) {
        // 執行指令
        command.exec();

        // 紀錄指令
        undoStack.push(command);

        // 每次執行指令時重置可還原的指令
        redoStack.clear();
    }

    // 復原
    public void undo() {
        if (!undoable()) {
            return;
        }

        // 取出最新一筆紀錄進行復原
        Command command = undoStack.pop();
        command.undo();

        // 紀錄一筆可重做的指令
        redoStack.push(command);
    }

    // 重做
    public void redo() {
        if (!redoable()) {
            return;
        }

        // 取出最新一筆紀錄進行重做
        Command command = redoStack.pop();
        command.redo();

        // 紀錄一筆可復原的指令
        undoStack.push(command);
    }

    // 重置
    public void reset() {
        undoStack.clear();
        redoStack.clear();
    }
}
