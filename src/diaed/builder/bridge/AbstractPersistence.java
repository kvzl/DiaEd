package diaed.builder.bridge;

import diaed.model.DiagramElement;

/**
 * Created by ucfan on 2017/5/29.
 */
public abstract class AbstractPersistence {
    private PersistenceImplementor impl;

    protected PersistenceImplementor getImpl() {
        return impl;
    }

    public AbstractPersistence(PersistenceImplementor impl) {
        this.impl = impl;
    }

    abstract public void save(String id, DiagramElement element);
    abstract public DiagramElement findById(String id);
}
