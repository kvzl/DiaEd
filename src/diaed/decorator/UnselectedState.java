package diaed.decorator;

import diaed.view.EditableText;
import diaed.view.StateView;
import javafx.scene.shape.Circle;

/**
* Created by ucfan on 2017/5/29.
*/
public class UnselectedState extends StateDecorator {
    public UnselectedState(StateView component) {
        super(component);
        component.getStyleClass().add("unselected");
        component.getStyleClass().remove("selected");
    }
}
