package application.model;

import application.DragHandler;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
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
    Point2D destination;

    DragHandler dragHandlerStart = new DragHandler();
    DragHandler dragHandlerEnd = new DragHandler();

    Arrow arrow;

    public Point2D getDestination() {
        return destination;
    }

    public void setDestination(Point2D p) {
        destination = p;
    }

    public Transition() {
        super(new Point2D(150, 150));
        setDestination(new Point2D(150, 250));
    }

    public Transition(Point2D position, Point2D destination) {
        super(position);
        setDestination(destination);
    }

    @Override
    public void draw(Pane canvas) {
        System.out.println("draw transition");

        Point2D start = getPosition();
        Point2D end = getDestination();

        Line line = new Line(
                start.getX(),
                start.getY(),
                end.getX(),
                end.getY()
        );

        arrow = new Arrow(line, new Line(), new Line());

        arrow.getStyleClass().add("transition");

        arrow.setEnd(end.getX(), end.getY());
        arrow.setStart(start.getX(), start.getY());


        DragPoint p1 = new DragPoint(start, dragHandlerStart);
        p1.setOnDragged(t -> {
            relocateStart(t.getSceneX(), t.getSceneY());
        });

        DragPoint p2 = new DragPoint(end, dragHandlerEnd);
        p2.setOnDragged(t -> {
            relocateEnd(t.getSceneX(), t.getSceneY());
        });

        shape = new Group(arrow, p1, p2);

        canvas.getChildren().add(shape);
    }

    public void relocateStart(double x, double y) {
        arrow.setStart(x, y - 40);
        setPosition(new Point2D(x, y - 40));
    }

    public void relocateEnd(double x, double y) {
        arrow.setEnd(x, y - 40);
        setDestination(new Point2D(x, y - 40));
    }


    class Arrow extends Group {

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
                } else {
                    double factor = arrowLength / Math.hypot(sx-ex, sy-ey);
                    double factorO = arrowWidth / Math.hypot(sx-ex, sy-ey);

                    // part in direction of main line
                    double dx = (sx - ex) * factor;
                    double dy = (sy - ey) * factor;

                    // part ortogonal to main line
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

        // start/end properties

        public void setStart(double startX, double startY) {
            line.setStartX(startX);
            line.setStartY(startY);
        }
        public void setEnd(double endX, double endY) {
            line.setEndX(endX);
            line.setEndY(endY);
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

    class DragPoint extends Circle {
        DragHandler dragHandler;
        public DragPoint(Point2D position, DragHandler dragHandler) {
            super(position.getX(), position.getY(), 5);
            this.getStyleClass().add("transition-drag-point");
            this.dragHandler = dragHandler;
            this.setOnPressed(null);
            this.setOnDragged(null);
        }

        public void setOnPressed(EventHandler<MouseEvent> handler) {
            if (handler == null) {
                this.setOnMousePressed(dragHandler.getOnPressed());
            }
            else {
                this.setOnMousePressed(event -> {
                    dragHandler.getOnPressed().handle(event);
                    handler.handle(event);
                });
            }
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





