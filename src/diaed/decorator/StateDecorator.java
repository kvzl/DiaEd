package diaed.decorator;

import diaed.view.EditableText;
import diaed.view.StateView;
import javafx.scene.shape.Circle;

/**
 * Created by ucfan on 2017/5/29.
 */
public abstract class StateDecorator extends StateView {
    private StateView component;

    public StateDecorator(StateView component) {
        this.component = component;
        getChildren().add(component);
    }

    @Override
    public void create() {
        component.create();
    }

    @Override
    public Circle getCircle() {
        return component.getCircle();
    }

    @Override
    public EditableText getText() {
        return component.getText();
    }

    @Override
    public StateView getInstance() {
        return component;
    }
}
