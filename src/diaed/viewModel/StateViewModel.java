package diaed.viewModel;

import diaed.Store;
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
    protected void beforeUpdate() {

    }

    @Override
    protected void created() {
        model.addListener(((observable, oldValue, newValue) -> {
            State state = observable.getValue();
            System.out.println(state.getPositionX() + ", " + state.getPositionY() + ": " + state.getName());
        }));
    }


    @Override
    protected void createView() {
        view = new StateView(model.get());
        view.create();
    }

    @Override
    protected void bindListeners() {
        State model = this.model.get();

        Circle circle = view.getCircle();

        EditableText text = view.getText();
        text.setText(model.getName());


        dragHandler = new DragHandler(view);

        // 透過同步 translate 值，使元件可以被拖移
        dragHandler.bindToPoint(view);
//        dragHandler.bindToPoint(model.positionXProperty(), model.positionYProperty());

        // 按下時準備拖曳
        circle.setOnMousePressed(event -> {
            store.saveHistory();
            store.setSelected(model);
            dragHandler.getOnPressed().handle(event);
        });

        // 拖曳中
        circle.setOnMouseDragged(dragHandler.getOnDragged());

        circle.setOnMouseReleased(event -> {
            System.out.println("release");
            model.positionXProperty().set(model.getPositionX() + dragHandler.getTranslateX());
            model.positionYProperty().set(model.getPositionY() + dragHandler.getTranslateY());
        });

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
            store.setSelected(model);
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

    @Override
    protected void draw() {
        store.draw(view);
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

