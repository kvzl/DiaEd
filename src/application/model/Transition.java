package application.model;

import application.DragHandler;
import application.Store;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;


/**
 * Created by ucfan on 2017/3/28.
 */
public class Transition extends DiagramElement {
    private final double LENGTH = 150;

    private DoubleProperty destinationX;
    private DoubleProperty destinationY;

    private Arrow arrow;
    private DragPoint p1;
    private DragPoint p2;
    private DragHandler rootDragHandler;


    public Transition() {
        super(new Point2D(150, 150));
        destinationX = new SimpleDoubleProperty(150 + LENGTH);
        destinationY = new SimpleDoubleProperty(150);
    }

    public Transition(Point2D position, Point2D destination) {
        super(position);
        destinationX = new SimpleDoubleProperty(destination.getX());
        destinationY = new SimpleDoubleProperty(destination.getY());
    }

    public double getDestinationX() {
        return destinationX.get();
    }

    public void setDestinationX(double x) {
        destinationX.set(x);
    }

    public double getDestinationY() {
        return destinationY.get();
    }

    public void setDestinationY(double y) {
        destinationY.set(y);
    }

    public DoubleProperty destinationXProperty() {
        return destinationX;
    }

    public DoubleProperty destinationYProperty() {
        return destinationY;
    }

    @Override
    public void draw(Store store) {
        Pane canvas = store.getCanvas();

        arrow = new Arrow(
                new Line(getPositionX(), getPositionY(), getDestinationX(), getDestinationY()),
                new Line(),
                new Line()
        );
        arrow.getStyleClass().add("transition");

        rootDragHandler = new DragHandler(arrow);

        p1 = new DragPoint(positionXProperty(), positionYProperty());
        p2 = new DragPoint(destinationXProperty(), destinationYProperty());

        shape = new Group(arrow, p1, p2);

        bindListeners(store);
        canvas.getChildren().add(shape);
    }

    private void bindListeners(Store store) {
        p1.setOnDragged(event -> {
            arrow.setStartX(getPositionX() - arrow.getTranslateX());
            arrow.setStartY(getPositionY() - arrow.getTranslateY());
        });

        p2.setOnDragged(event -> {
            arrow.setEndX(getDestinationX() - arrow.getTranslateX());
            arrow.setEndY(getDestinationY() - arrow.getTranslateY());
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
        DragHandler dragHandler = new DragHandler(this);

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





