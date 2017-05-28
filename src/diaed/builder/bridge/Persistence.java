package diaed.builder.bridge;

import diaed.model.DiagramElement;

/**
 * Created by ucfan on 2017/5/29.
 */
public class Persistence extends AbstractPersistence {

    public Persistence(PersistenceImplementor impl) {
        super(impl);
    }

    @Override
    public void save(String id, DiagramElement element) {
        getImpl().saveObject(id, element);
    }

    @Override
    public DiagramElement findById(String id) {
        return getImpl().getObject(id);
    }
}
