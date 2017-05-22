package diaed.viewModel;

import diaed.Store;
import diaed.model.DiagramElement;
import javafx.scene.Node;

/**
 * Created by ucfan on 2017/4/3.
 */

/**
 * View Model 負責資料綁定、事件綁定及 View 的繪製
 */

abstract public class ViewModel<T extends DiagramElement> {
    protected Node shape;
    protected T model;

    public ViewModel(T model) {
        this.model = model;
    }

    public T getModel() {
        return model;
    }

    abstract public void draw(Store store);
}
