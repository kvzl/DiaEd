package diaed.builder;

import diaed.model.DiagramElement;

/**
 * Created by Administrator on 2017/5/26.
 */
public interface AbstractBuilder<T extends DiagramElement> {
    T build();
    AbstractBuilder setPosition(double X, double Y);
    AbstractBuilder setName(String name);
    AbstractBuilder setDestination(double X, double Y);
}
