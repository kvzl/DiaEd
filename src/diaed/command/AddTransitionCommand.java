package diaed.command;

import diaed.Store;
import diaed.model.Transition;

/**
* Created by ucfan on 2017/5/26.
*/
public class AddTransitionCommand extends Command {

    public AddTransitionCommand(Store store) {
        super(store);
    }

    @Override
    public void beforeExecute() {
        memento = diagram.save();
    }

    @Override
    public void execute() {
        memento = diagram.save();

        Transition transition = new Transition();
        diagram.add(transition);
    }
}
