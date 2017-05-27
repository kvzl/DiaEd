package diaed.command;

import diaed.Store;
import diaed.history.Memento;
import diaed.model.StateDiagram;

/**
 * Created by ucfan on 2017/5/26.
 */
public abstract class Command {
    protected Memento memento;
    protected Store store;
    protected StateDiagram diagram;

    public Command(Store store) {
        this.store = store;
        this.diagram = store.getDiagram();
    }

    public final void exec() {
        beforeExecute();
        execute();
        afterExecute();
    }

    // primitive
    public abstract void execute();

    // hooks
    public void beforeExecute() { }
    public void afterExecute() { }

    public void undo() {
        Memento undoMemento = diagram.save();
        diagram.restore(memento);
        memento = undoMemento;
    }

    public void redo() {
        Memento redoMemento = diagram.save();
        diagram.restore(memento);
        memento = redoMemento;
    }

}
