package diaed.decorator;

import diaed.view.EditableText;
import diaed.view.StateView;
import javafx.scene.shape.Circle;

/**
* Created by ucfan on 2017/5/29.
*/
public class SelectedState extends StateDecorator {
    public SelectedState(StateView component) {
        super(component);
        component.getStyleClass().add("selected");
        component.getStyleClass().remove("unselected");
    }
}
