package diaed.command;

import diaed.Store;

/**
* Created by ucfan on 2017/5/26.
*/
public class EditElementCommand extends Command {

    public EditElementCommand(Store store) {
        super(store);
    }
    @Override
    public void execute() {
        store.setEditing(store.getSelectedElement());
    }
}
