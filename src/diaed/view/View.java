package diaed.view;

import javafx.scene.Group;

/**
 * Created by ucfan on 2017/5/24.
 */
public abstract class View extends Group {
    abstract public void create();
    public View getInstance() {
        return this;
    }
}
