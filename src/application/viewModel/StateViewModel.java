package application.viewModel;

import application.Store;
import application.model.State;
import application.view.Canvas;
import javafx.scene.shape.Circle;

/**
 * Created by ucfan on 2017/4/3.
 */
public class StateViewModel extends ViewModel<State> {
    private DragHandler dragHandler;

    public StateViewModel(State model) {
        super(model);
    }

    @Override
    public void draw(Store store) {
        shape = new Circle(model.getPositionX(), model.getPositionY(), 60);
        shape.getStyleClass().add("state-circle");

        bindListeners(store);

        store.draw(shape);
    }

    private void bindListeners(Store store) {
        dragHandler = new DragHandler(shape);

        dragHandler.bindToPoint(shape, model.positionXProperty(), model.positionYProperty());

        shape.setOnMousePressed(event -> {
            store.saveHistory();
            dragHandler.getOnPressed().handle(event);
        });
        shape.setOnMouseDragged(dragHandler.getOnDragged());

        shape.setOnMouseClicked(event -> {
            store.setSelected(model);
            event.consume();
        });

        store.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == model) {
                shape.getStyleClass().add("selected");
            }
            else {
                shape.getStyleClass().remove("selected");
            }
        });

    }


}
