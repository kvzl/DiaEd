package diaed.command;

import diaed.Store;

/**
* Created by ucfan on 2017/5/26.
*/
public class DeleteElementCommand extends Command {
    public DeleteElementCommand(Store store) {
        super(store);
    }

    @Override
    public void beforeExecute() {
        memento = diagram.save();
    }

    @Override
    public void execute() {
        diagram.remove(store.getSelected());
        store.setSelected(null);
    }
}
