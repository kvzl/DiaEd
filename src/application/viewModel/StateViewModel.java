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
        Canvas canvas = store.getCanvas();

        shape = new Circle(model.getPositionX(), model.getPositionY(), 60);
        shape.getStyleClass().add("state-circle");

        bindListeners(store);

        canvas.getChildren().add(shape);
    }


    private void bindListeners(Store store) {
        dragHandler = new DragHandler(shape);

        dragHandler.bindToPoint(shape, model.positionXProperty(), model.positionYProperty());

        shape.setOnMousePressed(dragHandler.getOnPressed());
        shape.setOnMouseDragged(dragHandler.getOnDragged());

        shape.setOnMouseClicked(event -> {
            store.setSelected(shape);
            event.consume();
        });

        store.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == shape) {
                shape.getStyleClass().add("selected");
            }
            else {
                shape.getStyleClass().remove("selected");
            }
        });

    }


}
