package application.viewModel;

import application.Store;
import application.model.Transition;
import application.view.Canvas;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 * Created by ucfan on 2017/4/3.
 */
public class TransitionViewModel extends ViewModel<Transition> {
    private Arrow arrow;
    private DragPoint p1;
    private DragPoint p2;
    private DragHandler rootDragHandler;

    public TransitionViewModel(Transition model) {
        super(model);
    }

    @Override
    public void draw(Store store) {
        Canvas canvas = store.getCanvas();

        arrow = new Arrow(
                new Line(model.getPositionX(), model.getPositionY(), model.getDestinationX(), model.getDestinationY()),
                new Line(),
                new Line()
        );
        arrow.getStyleClass().add("transition");

        p1 = new DragPoint(model.positionXProperty(), model.positionYProperty());
        p2 = new DragPoint(model.destinationXProperty(), model.destinationYProperty());

        shape = new Group(arrow, p1, p2);

        bindListeners(store);
        canvas.getChildren().add(shape);
    }

    private void bindListeners(Store store) {
        rootDragHandler = new DragHandler(arrow);

        p1.setOnDragged(event -> {
            arrow.setStartX(model.getPositionX() - arrow.getTranslateX());
            arrow.setStartY(model.getPositionY() - arrow.getTranslateY());
        });

        p2.setOnDragged(event -> {
            arrow.setEndX(model.getDestinationX() - arrow.getTranslateX());
            arrow.setEndY(model.getDestinationY() - arrow.getTranslateY());
        });

        arrow.setOnMousePressed(event -> {
            p1.dragHandler.getOnPressed().handle(event);
            p2.dragHandler.getOnPressed().handle(event);
            rootDragHandler.getOnPressed().handle(event);
        });

        arrow.setOnMouseDragged(event -> {
            p1.dragHandler.getOnDragged().handle(event);
            p2.dragHandler.getOnDragged().handle(event);
            rootDragHandler.getOnDragged().handle(event);
        });

        rootDragHandler.bindToPoint(arrow);

        arrow.setOnMouseClicked(event -> {
            store.setSelected(arrow);
            event.consume();
        });

        store.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == arrow) {
                arrow.getStyleClass().add("selected");
            }
            else {
                arrow.getStyleClass().remove("selected");
            }
        }));
    }

    private class Arrow extends Group {
        private final Line line;

        private static final double ARROW_LENGTH = 20;
        private static final double ARROW_WIDTH = 7;

        private Arrow(Line line, Line arrow1, Line arrow2) {
            super(line, arrow1, arrow2);
            this.line = line;

            InvalidationListener updater = o -> {
                double ex = getEndX();
                double ey = getEndY();
                double sx = getStartX();
                double sy = getStartY();

                arrow1.setEndX(ex);
                arrow1.setEndY(ey);
                arrow2.setEndX(ex);
                arrow2.setEndY(ey);

                if (ex == sx && ey == sy) {
                    arrow1.setStartX(ex);
                    arrow1.setStartY(ey);
                    arrow2.setStartX(ex);
                    arrow2.setStartY(ey);
                }
                else {
                    double factor = ARROW_LENGTH / Math.hypot(sx-ex, sy-ey);
                    double factorO = ARROW_WIDTH / Math.hypot(sx-ex, sy-ey);
                    double dx = (sx - ex) * factor;
                    double dy = (sy - ey) * factor;
                    double ox = (sx - ex) * factorO;
                    double oy = (sy - ey) * factorO;

                    arrow1.setStartX(ex + dx - oy);
                    arrow1.setStartY(ey + dy + ox);
                    arrow2.setStartX(ex + dx + oy);
                    arrow2.setStartY(ey + dy - ox);
                }
            };

            startXProperty().addListener(updater);
            startYProperty().addListener(updater);
            endXProperty().addListener(updater);
            endYProperty().addListener(updater);
            updater.invalidated(null);
        }

        public final void setStartX(double value) {
            line.setStartX(value);
        }

        public final double getStartX() {
            return line.getStartX();
        }

        public final DoubleProperty startXProperty() {
            return line.startXProperty();
        }

        public final void setStartY(double value) {
            line.setStartY(value);
        }

        public final double getStartY() {
            return line.getStartY();
        }

        public final DoubleProperty startYProperty() {
            return line.startYProperty();
        }

        public final void setEndX(double value) {
            line.setEndX(value);
        }

        public final double getEndX() {
            return line.getEndX();
        }

        public final DoubleProperty endXProperty() {
            return line.endXProperty();
        }

        public final void setEndY(double value) {
            line.setEndY(value);
        }

        public final double getEndY() {
            return line.getEndY();
        }

        public final DoubleProperty endYProperty() {
            return line.endYProperty();
        }

    }

    private class DragPoint extends Circle {
        private DragHandler dragHandler = new DragHandler(this);

        public DragPoint(DoubleProperty xProperty, DoubleProperty yProperty) {
            super(xProperty.get(), yProperty.get(), 5);

            this.getStyleClass().add("transition-drag-point");

            dragHandler.bindToPoint(this, xProperty, yProperty);

            this.setOnMousePressed(dragHandler.getOnPressed());
            this.setOnMouseDragged(dragHandler.getOnDragged());
        }

        public void setOnDragged(EventHandler<MouseEvent> handler) {
            if (handler == null) {
                this.setOnMouseDragged(dragHandler.getOnDragged());
            }
            else {
                this.setOnMouseDragged(event -> {
                    dragHandler.getOnDragged().handle(event);
                    handler.handle(event);
                });
            }
        }
    }
}
