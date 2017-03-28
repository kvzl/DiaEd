package application.model;

import javafx.scene.layout.Pane;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created by ucfan on 2017/3/28.
 */
public class StateDiagram extends DiagramElement implements Iterable<DiagramElement> {
    List<DiagramElement> children = new ArrayList<>();

    public void add(DiagramElement element) {
        children.add(element);
    }

    public void remove(DiagramElement element) {
        children.remove(element);
    }

    public DiagramElement get(int index) {
        return children.get(index);
    }


    @Override
    public void draw(Pane pane) {
    }

    @Override
    public Iterator<DiagramElement> iterator() {
        return new StateDiagramIterator();
    }

    @Override
    public void forEach(Consumer<? super DiagramElement> action) {
        for (DiagramElement element: children) {
            action.accept(element);
        }
    }

    @Override
    public Spliterator<DiagramElement> spliterator() {
        return null;
    }

    class StateDiagramIterator implements Iterator<DiagramElement> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return (index < children.size());
        }

        @Override
        public DiagramElement next() {
            if (hasNext()) {
                return children.get(index++);
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void remove() {

        }

        @Override
        public void forEachRemaining(Consumer<? super DiagramElement> action) {

        }
    }

}
