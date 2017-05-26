package diaed.view;

import diaed.model.StateDiagram;

/**
 * Created by ucfan on 2017/4/3.
 */

public class StateDiagramView extends View {
    private StateDiagram model;
    public StateDiagramView(StateDiagram model) {
        this.model = model;
    }

    @Override
    public void create() {}
}
