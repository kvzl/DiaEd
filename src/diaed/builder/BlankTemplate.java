package diaed.builder;

import diaed.model.StateDiagram;

/**
 * Created by ucfan on 2017/5/28.
 */
public class BlankTemplate implements DiagramTemplate {
    @Override
    public void construct() {
    }

    @Override
    public StateDiagram getDiagram() {
        return new StateDiagram();
    }

    @Override
    public String getName() {
        return "Blank";
    }
}
