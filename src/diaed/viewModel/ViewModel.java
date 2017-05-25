package diaed.viewModel;

import diaed.Store;
import diaed.model.DiagramElement;
import diaed.view.View;

/**
 * Created by ucfan on 2017/4/3.
 */

/**
 * View Model 負責資料綁定、事件綁定及 View 的繪製
 */

abstract public class ViewModel<M extends DiagramElement, V extends View> {
    protected M model;
    protected V view;
    protected Store store;

    public ViewModel(Store store, M model, V view) {
        this.store = store;
        this.model = model;
        this.view = view;

        initialize();
    }

    public M getModel() {
        return model;
    }

    public V getView() { return view; }

    public void initialize() {
        draw();
        bindListeners();
        mounted();
    }

    protected void draw() {
        view.draw();
    }
    protected void bindListeners() {};
    protected void mounted() {
        store.draw(view);
    }
}
