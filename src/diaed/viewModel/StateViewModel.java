package diaed.viewModel;

import diaed.Store;
import diaed.model.State;
import diaed.util.DragHandler;
import diaed.view.EditableText;
import diaed.view.StateView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;

/**
 * Created by ucfan on 2017/4/3.
 */
public class StateViewModel extends ViewModel<State, StateView> {
    private DragHandler dragHandler;

    public StateViewModel(Store store, State model) {
        super(store, model, new StateView(model));
    }

    @Override
    protected void bindListeners() {
        Circle circle = view.getCircle();
        EditableText text = view.getText();

        dragHandler = new DragHandler(view);

        dragHandler.bindToPoint(view);
        dragHandler.bindToPoint(model.positionXProperty(), model.positionYProperty());

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

        text.setOnKeyIn(event -> {
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
                setCircleSelected(true);
            }
            else {
                setCircleSelected(false);
            }
        });

        store.editingProperty().addListener(((observable, oldValue, newValue) -> {
            setTextEditable((newValue == model));
        }));

    }

    public void setCircleSelected(boolean selected) {
        Circle circle = view.getCircle();

        if (selected) {
            circle.getStyleClass().add("selected");
        }
        else {
            circle.getStyleClass().remove("selected");
        }
    }

    public void setTextEditable(boolean editable) {
        view.getText().setEditable(editable);
    }

}

