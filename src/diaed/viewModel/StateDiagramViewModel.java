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
        Iterator<DiagramElement> iter = model.iterator();

        while (iter.hasNext()) {
            DiagramElement element = iter.next();
            String type = element.getClass().getSimpleName();

            // 根據 element 的類型建立對應的 View Model
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

}
