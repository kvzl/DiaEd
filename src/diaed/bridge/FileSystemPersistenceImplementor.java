package diaed.bridge;

import diaed.model.DiagramElement;
import diaed.model.StateDiagram;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by ucfan on 2017/5/29.
 */
public class FileSystemPersistenceImplementor extends PersistenceImplementor {
    @Override
    public void saveObject(String id, DiagramElement element) {
        FileOutputStream fout;
        ObjectOutputStream oos = null;

        try {
            fout = new FileOutputStream(id);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(element.serialize());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public DiagramElement getObject(String id) {
        FileInputStream fin;
        ObjectInputStream ois = null;

        try {
            fin = new FileInputStream(id);
            ois = new ObjectInputStream(fin);
            StateDiagram.SerializableElement loaded = (StateDiagram.SerializableElement) ois.readObject();

            return loaded.deserialize();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
