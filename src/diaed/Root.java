package diaed;

import diaed.model.StateDiagram;
import diaed.view.Canvas;
import diaed.view.RootView;
import diaed.view.Toolbar;
import diaed.viewModel.StateDiagramViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * Created by ucfan on 2017/5/26.
 */
public class Root {
    private Store store;
    private RootView view;
    private StateDiagramViewModel stateDiagramVM;


    public void setModel(StateDiagram diagram) {
        stateDiagramVM.setModel(diagram);
    }

    public StateDiagram getModel() {
        return stateDiagramVM.getModel();
    }

    public ObjectProperty<StateDiagram> modelProperty() {
        return stateDiagramVM.modelProperty();
    }

    public Root(Store store, Stage primaryStage) {
        this.store = store;
        store.setRoot(this);

        this.view = new RootView(primaryStage);
        view.setCanvas(new Canvas(store));
        view.setToolbar(new Toolbar(store));
    }


    public void clear() {
        view.getCanvas().clear();
    }

    public void add(Node node) {
        view.getCanvas().getChildren().add(node);
    }


    // 重新繪製畫面
    // NOTE: 可優化成只重新繪製有差異的部分
    public void redraw() {
        clear();
        stateDiagramVM.update();
    }

    public void initialize() {
        StateDiagram diagram = new StateDiagram();
        diagram.addListener(c -> redraw());
        stateDiagramVM = new StateDiagramViewModel(store, diagram);
        stateDiagramVM.modelProperty().addListener(((observable, oldValue, newValue) -> {
            observable.getValue().addListener(c -> redraw());
            redraw();
        }));
        store.setDiagram(stateDiagramVM.getModel());
    }
}
