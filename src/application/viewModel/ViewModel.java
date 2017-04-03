package application.viewModel;

import application.Store;
import application.model.DiagramElement;
import javafx.scene.Node;

/**
 * Created by ucfan on 2017/4/3.
 */

abstract public class ViewModel<T> extends DiagramElement {
    protected Node shape;
    protected T model;

    public ViewModel(T model) {
        this.model = model;
    }

    abstract public void draw(Store store);
}
