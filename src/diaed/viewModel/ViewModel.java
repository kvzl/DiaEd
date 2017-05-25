package diaed.viewModel;

import diaed.Store;
import diaed.view.View;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by ucfan on 2017/4/3.
 */

/**
 * View Model 負責資料綁定、事件綁定及 View 的繪製
 */

abstract public class ViewModel<M, V extends View> {
    protected ObjectProperty<M> model;
    protected V view;
    protected Store store;

    public ViewModel(Store store, M model, V view) {
        this.store = store;
        this.model = new SimpleObjectProperty<M>(model);
        this.view = view;

        initialize();
    }

    public void setModel(M model) {
        this.model.set(model);
    }

    public M getModel() {
        return model.get();
    }

    public ObjectProperty<M> modelProperty() {
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
