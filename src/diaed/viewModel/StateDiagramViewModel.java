package diaed.viewModel;

import diaed.Store;
import diaed.model.DiagramElement;
import diaed.model.State;
import diaed.model.StateDiagram;
import diaed.model.Transition;
import diaed.util.Iterator;
import diaed.view.StateDiagramView;

public class StateDiagramViewModel extends ViewModel<StateDiagram, StateDiagramView> {
    public StateDiagramViewModel(Store store, StateDiagram model) {
        super(store, model, new StateDiagramView(model));
    }

    @Override
    protected void draw() {
        StateDiagram model = this.model.get();

        Iterator<DiagramElement> iter = model.iterator();

        while (iter.hasNext()) {
            DiagramElement element = iter.next();
            String type = element.getClass().getSimpleName();

            switch (type) {
                case "State":
                    new StateViewModel(store, (State)element);
                    break;
                case "Transition":
                    new TransitionViewModel(store, (Transition)element);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void bindListeners() {
    }
}
