package diaed.builder;

import diaed.model.DiagramElement;

/**
 * Created by Administrator on 2017/5/26.
 */
public interface Builder<T extends DiagramElement> {
    T build();
    Builder setPosition(double X, double Y);
    Builder setName(String name);
    Builder setDestination(double X, double Y);
}
