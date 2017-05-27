package diaed.command;

import diaed.Store;
import diaed.model.State;

/**
* Created by ucfan on 2017/5/26.
*/
public class AddStateCommand extends Command {

    public AddStateCommand(Store store) {
        super(store);
    }

    @Override
    public void beforeExecute() {
        memento = diagram.save();
    }

    @Override
    public void execute() {
        State state = new State();
        diagram.add(state);
    }
}
