package diaed.viewModel;

import diaed.Store;
import diaed.decorator.SelectedState;
import diaed.decorator.UnselectedState;
import diaed.model.State;
import diaed.util.DragHandler;
import diaed.view.EditableText;
import diaed.view.StateView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

/**
 * Created by ucfan on 2017/4/3.
 */
public class StateViewModel extends ViewModel<State, StateView> {
    private DragHandler dragHandler;

    public StateViewModel(Store store, State model) {
        super(store, model);
    }


    @Override
    protected void createView() {
        view = new UnselectedState(new StateView(model.get()));
        view.create();
    }

    @Override
    protected void bindListeners() {
        State model = this.model.get();

        Circle circle = view.getCircle();

        EditableText text = view.getText();
        text.setText(model.getName());


        dragHandler = new DragHandler(view.getInstance());

        // 透過同步 translate 值，使元件可以被拖移
        dragHandler.bindToPoint(view.getInstance());
        dragHandler.bindToPoint(model.positionXProperty(), model.positionYProperty());

        // 按下時準備拖曳
        circle.setOnMousePressed(event -> {
            store.saveHistory();
            store.setSelectedElement(model);
            dragHandler.getOnPressed().handle(event);
        });

        // 拖曳中
        circle.setOnMouseDragged(dragHandler.getOnDragged());

//        circle.setOnMouseReleased(event -> {
//            model.positionXProperty().set(model.getPositionX());
//            model.positionYProperty().set(model.getPositionY());
//        });

        // 圓圈點兩下可編輯文字
        circle.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                store.setEditing(model);
            }
            else {
                store.setEditing(null);
            }

            event.consume();
        });

        // 點擊文字可選取，點兩下編輯
        text.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                store.setEditing(model);
            }
            store.setSelectedElement(model);
            event.consume();
        });


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

        // 輸入文字時更新 model
        text.textProperty().addListener(((observable, oldValue, newValue) -> {
            State newModel = model.clone();
            newModel.setName(newValue);
            setModel(newModel);
        }));


        store.selectedElementProperty().addListener((observable, oldValue, newValue) -> {
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

    @Override
    protected void draw() {
        store.draw(view);
    }

    public void setCircleSelected(boolean selected) {
        Circle circle = view.getCircle();

        if (selected) {
            store.destroy(view);
            view = new SelectedState((StateView)view.getInstance());
            store.draw(view);

        }
        else {
            store.destroy(view);
            view = new UnselectedState((StateView)view.getInstance());
            store.draw(view);
        }
    }


    public void setTextEditable(boolean editable) {
        view.getText().setEditable(editable);
    }

}


