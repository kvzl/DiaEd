package diaed.viewModel;

import diaed.Store;
import diaed.model.DiagramElement;
import diaed.view.View;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by ucfan on 2017/4/3.
 */

/**
 * View Model 負責資料綁定、事件綁定及 View 的繪製
 */

abstract public class ViewModel<M extends DiagramElement, V extends View> {
    protected ObjectProperty<M> model;
    protected V view;
    protected Store store;

    public ViewModel(Store store, M model) {
        this.store = store;
        this.model = new SimpleObjectProperty<M>(model);

        initialize();
    }

    public void setModel(M newModel) {
        beforeUpdate();
        this.model.set(newModel);
        updated();
    }

    public M getModel() {
        return model.get();
    }

    public ObjectProperty<M> modelProperty() {
        return model;
    }

    public V getView() { return view; }

    private void initialize() {
        beforeCreate();
        createView();
        bindListeners();
        draw();
        created();
    }

    public void update() {
        beforeUpdate();
        createView();
        bindListeners();
        draw();
        updated();
    }


    protected abstract void createView();
    protected abstract void draw();
    protected abstract void bindListeners();

    /* hooks */

    protected void beforeCreate() {}
    protected void created() {}
    protected void beforeUpdate() {}
    protected void updated() {}
}
