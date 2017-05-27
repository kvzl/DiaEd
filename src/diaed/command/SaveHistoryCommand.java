package diaed.command;

import diaed.Store;

/**
* Created by ucfan on 2017/5/26.
*/
public class SaveHistoryCommand extends Command {

    public SaveHistoryCommand(Store store) {
        super(store);
    }
    @Override
    public void execute() {
        memento = diagram.save();
    }
}
