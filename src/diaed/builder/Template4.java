package diaed.builder;

import diaed.model.DiagramElement;
import diaed.model.State;
import diaed.model.StateDiagram;
import diaed.model.Transition;

/**
 * Created by Administrator on 2017/5/26.
 */
public class Template4 implements DiagramTemplate {
    private StateDiagram diagram = new StateDiagram();

    @Override
    public String getName() {
        return "Template4";
    }

    public Template4() {
        construct();
    }

    public void construct(){
        DiagramElement state1 = new State.Builder()
                .setName("State1")
                .setPosition(200, 300)
                .build();

        DiagramElement state2 = new State.Builder()
                .setName("State2")
                .setPosition(600, 300)
                .build();

        DiagramElement trans2 = new Transition.Builder()
                .setName("trans2")
                .setPosition(260, 300)
                .setDestination(540, 300)
                .build();

        diagram.add(state1);
        diagram.add(state2);
        diagram.add(trans2);
    }

    @Override
    public StateDiagram getDiagram() {
        return diagram;
    }

    @Override
    public String getThumb() {
        return "@startup/template.png";
    }
}
