package diaed.viewModel;

import diaed.Store;
import diaed.model.State;
import diaed.view.EditableText;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;

/**
 * Created by ucfan on 2017/4/3.
 */
public class StateViewModel extends ViewModel<State> {
    private DragHandler dragHandler;

    // 圓圈部分
    private Circle circle;

    // 文字部分
    private EditableText text;

    public StateViewModel(State model) {
        super(model);
    }

    @Override
    public void draw(Store store) {
        circle = new Circle(model.getPositionX(), model.getPositionY(), 60);
        circle.getStyleClass().add("state-circle");

        text = new EditableText(model.getPositionX(), model.getPositionY());
        text.setText(model.getName());

        shape = new Group(circle, text);

        bindListeners(store);

        store.draw(shape);
    }

    private void bindListeners(Store store) {
        dragHandler = new DragHandler(shape);

        dragHandler.bindToPoint(shape, model.positionXProperty(), model.positionYProperty());

        circle.setOnMousePressed(event -> {
            store.saveHistory();
            dragHandler.getOnPressed().handle(event);
        });
        circle.setOnMouseDragged(dragHandler.getOnDragged());

        circle.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                store.setEditing(model);
            }
            else {
                store.setEditing(null);
            }

            store.setSelected(model);
            event.consume();
        });

        text.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                store.setEditing(model);
            }
            store.setSelected(model);
            event.consume();
        });

        text.bindText(model.nameProperty());

        text.setOnPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                store.setEditing(null);
            }
            else {
                if (store.getEditing() != null) {
                    store.saveHistory();
                }
            }
        });

        store.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == model) {
                circle.getStyleClass().add("selected");
            }
            else {
                circle.getStyleClass().remove("selected");
            }
        });

        store.editingProperty().addListener(((observable, oldValue, newValue) -> {
            text.setEditable((newValue == model));
        }));

    }

}

