package diaed.builder.bridge;

import diaed.model.DiagramElement;

/**
 * Created by ucfan on 2017/5/29.
 */
public abstract class PersistenceImplementor {
    abstract public void saveObject(String id, DiagramElement element);
    abstract public DiagramElement getObject(String id);
}
