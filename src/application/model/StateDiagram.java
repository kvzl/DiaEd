package application.model;

import application.history.DiagramState;
import application.Store;
import application.history.Memento;
import application.history.Originator;
import application.viewModel.StateDiagramViewModel;

import java.util.ArrayList;
import java.util.List;

public class StateDiagram extends DiagramElement implements Iterable<DiagramElement>, Originator {
    private List<DiagramElement> children = new ArrayList<>();

    public void add(DiagramElement element) {
        children.add(element);
    }

    public void remove(DiagramElement element) {
        children.remove(element);
    }

    public DiagramElement get(int index) {
        return children.get(index);
    }

    public DiagramElement get(DiagramElement element) {
        int index = children.indexOf(element);
        if (index < 0) {
            return null;
        }
        return children.get(index);
    }

    @Override
    public void draw(Store store) {
        if (viewModel == null) {
            viewModel = new StateDiagramViewModel(this);
        }
        viewModel.draw(store);
    }

    @Override
    public Memento save() {
        Memento memento = new Memento();
        DiagramState backup = new DiagramState();

        Iterator<DiagramElement> iter = iterator();
        while (iter.hasNext()) {
            DiagramElement element = iter.next();
            backup.add(element.clone());
        }

        memento.setState(backup);
        return memento;
    }

    @Override
    public void restore(Memento memento) {
        children = memento.getState();
    }



    @Override
    public Iterator<DiagramElement> iterator() {
        return new StateDiagramIterator();
    }


    public class StateDiagramIterator implements Iterator<DiagramElement> {
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
            throw new UnsupportedOperationException("remove");
        }
    }


}
