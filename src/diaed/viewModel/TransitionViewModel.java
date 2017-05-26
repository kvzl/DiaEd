package diaed.viewModel;

import diaed.Store;
import diaed.model.Transition;
import diaed.util.DragHandler;
import diaed.view.EditableText;
import diaed.view.TransitionView;
import javafx.scene.input.KeyCode;

/**
 * Created by ucfan on 2017/4/3.
 */
public class TransitionViewModel extends ViewModel<Transition, TransitionView> {
    // 處理拖曳行為
    private DragHandler rootDragHandler;

    public TransitionViewModel(Store store, Transition model) {
        super(store, model);
    }


    @Override
    protected void createView() {
        view = new TransitionView(model.get());
        view.create();
    }

    @Override
    protected void bindListeners() {
        Transition model = this.model.get();

        TransitionView.Arrow arrow = view.getArrow();
        TransitionView.DragPoint startPoint = view.getStartPoint();
        TransitionView.DragPoint endPoint = view.getEndPoint();
        EditableText text = view.getText();

        rootDragHandler = new DragHandler(view.getArrow());

        // 拖曳前儲存狀態
        view.getStartPoint().setOnPressed(event -> {
            store.saveHistory();
        });
        // 拖曳點移動時更新箭頭位置
        startPoint.setOnDragged(event -> {
            arrow.setStartX(model.getPositionX() - arrow.getTranslateX());
            arrow.setStartY(model.getPositionY() - arrow.getTranslateY());
        });

        endPoint.setOnPressed(event -> {
            store.saveHistory();
        });
        endPoint.setOnDragged(event -> {
            arrow.setEndX(model.getDestinationX() - arrow.getTranslateX());
            arrow.setEndY(model.getDestinationY() - arrow.getTranslateY());
        });

        // 拖曳箭頭時同時移動拖曳點
        arrow.setOnMousePressed(event -> {
            store.saveHistory();
            startPoint.getDragHandler().getOnPressed().handle(event);
            endPoint.getDragHandler().getOnPressed().handle(event);
            text.getDragHandler().getOnPressed().handle(event);
            rootDragHandler.getOnPressed().handle(event);
        });
        arrow.setOnMouseDragged(event -> {
            startPoint.getDragHandler().getOnDragged().handle(event);
            endPoint.getDragHandler().getOnDragged().handle(event);
            text.getDragHandler().getOnDragged().handle(event);
            rootDragHandler.getOnDragged().handle(event);
        });

        text.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                store.setEditing(model);
                text.requestFocus();
            }
            store.setSelected(model);
            event.consume();
        });

        text.setOnKeyIn(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                store.setEditing(null);
            } else {
                if (store.getEditing() != null) {
                    store.saveHistory();
                }
            }
        });

        text.bindText(model.nameProperty());

        rootDragHandler.bindToPoint(arrow);

        arrow.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                store.setEditing(model);
                text.requestFocus();
            }

            store.setSelected(model);
            event.consume();
        });

        store.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == model) {
                arrow.getStyleClass().add("selected");
            } else {
                arrow.getStyleClass().remove("selected");
            }
        }));

        store.editingProperty().addListener(((observable, oldValue, newValue) -> {
            text.setEditable((newValue == model));
        }));

    }

    @Override
    protected void draw() {
        store.draw(view);
    }
}
