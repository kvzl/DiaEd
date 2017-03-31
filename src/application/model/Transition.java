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

    DoubleProperty destinationX;
    DoubleProperty destinationY;

    Arrow arrow;


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
        System.out.println("draw transition");

        double startX = getPositionX();
        double startY = getPositionY();

        double endX = getDestinationX();
        double endY = getDestinationY();

        arrow = new Arrow(
                new Line(startX, startY, endX, endY),
                new Line(),
                new Line()
        );

        arrow.getStyleClass().add("transition");


        arrow.startXProperty().bindBidirectional(positionXProperty());
        arrow.startYProperty().bindBidirectional(positionYProperty());
        arrow.endXProperty().bindBidirectional(destinationXProperty());
        arrow.endYProperty().bindBidirectional(destinationYProperty());


        DragPoint p1 = new DragPoint(new Point2D(startX, startY), t -> {
            setPositionX(t.getSceneX());
            setPositionY(t.getSceneY() - 40);
        });


        DragPoint p2 = new DragPoint(new Point2D(endX, endY), t -> {
            setDestinationX(t.getSceneX());
            setDestinationY(t.getSceneY() - 40);
        });


        shape = new Group(arrow, p1, p2);
        canvas.getChildren().add(shape);
    }


    private class Arrow extends Group {

        private final Line line;

        private static final double arrowLength = 20;
        private static final double arrowWidth = 7;

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
                    // arrow parts of length 0
                    arrow1.setStartX(ex);
                    arrow1.setStartY(ey);
                    arrow2.setStartX(ex);
                    arrow2.setStartY(ey);
                }
                else {
                    double factor = arrowLength / Math.hypot(sx-ex, sy-ey);
                    double factorO = arrowWidth / Math.hypot(sx-ex, sy-ey);

                    // part in direction of main line
                    double dx = (sx - ex) * factor;
                    double dy = (sy - ey) * factor;

                    // part orthogonal to main line
                    double ox = (sx - ex) * factorO;
                    double oy = (sy - ey) * factorO;

                    arrow1.setStartX(ex + dx - oy);
                    arrow1.setStartY(ey + dy + ox);
                    arrow2.setStartX(ex + dx + oy);
                    arrow2.setStartY(ey + dy - ox);
                }
            };

            // add updater to properties
            startXProperty().addListener(updater);
            startYProperty().addListener(updater);
            endXProperty().addListener(updater);
            endYProperty().addListener(updater);
            updater.invalidated(null);
        }

        // start/endProperty properties
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
        DragHandler dragHandler = new DragHandler();

        public DragPoint(Point2D position, EventHandler<MouseEvent> onDragged) {
            super(position.getX(), position.getY(), 5);
            this.getStyleClass().add("transition-drag-point");

            this.setOnMousePressed(dragHandler.getOnPressed());
            this.setOnMouseDragged(event -> {
                dragHandler.getOnDragged().handle(event);
                onDragged.handle(event);
            });
        }
    }

}





