package diaed.view;

import diaed.model.Transition;
import diaed.util.DragHandler;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 * Created by ucfan on 2017/5/24.
 */
public class TransitionView extends View {
    // 箭頭
    private Arrow arrow;

    // 拖曳點（尾端）
    private DragPoint startPoint;

    // 拖曳點（頭部）
    private DragPoint endPoint;

    // 文字部分
    private EditableText text;


    private DoubleProperty textX;
    private DoubleProperty textY;


    private Transition model;

    public TransitionView(Transition model) {
        this.model = model;
    }

    @Override
    public void create() {
        arrow = new Arrow(model.getPositionX(), model.getPositionY(), model.getDestinationX(), model.getDestinationY());
        arrow.getStyleClass().add("transition");

        startPoint = new DragPoint(model.positionXProperty(), model.positionYProperty());
        endPoint = new DragPoint(model.destinationXProperty(), model.destinationYProperty());

        textX = new SimpleDoubleProperty((model.getPositionX() + model.getDestinationX()) / 2);
        textY = new SimpleDoubleProperty((model.getPositionY() + model.getDestinationY()) / 2 - 15);
        text = new EditableText(textX, textY);
        text.setText(model.getName());
        text.setWidth(150);


        this.getChildren().add(arrow);
        this.getChildren().add(startPoint);
        this.getChildren().add(endPoint);
        this.getChildren().add(text);
    }

    public Arrow getArrow() {
        return arrow;
    }

    public DragPoint getStartPoint() {
        return startPoint;
    }

    public DragPoint getEndPoint() {
        return endPoint;
    }

    public EditableText getText() {
        return text;
    }


    public class Arrow extends Group {
        private final Line line;
        private final Line arrow1 = new Line();
        private final Line arrow2 = new Line();

        private static final double ARROW_LENGTH = 20;
        private static final double ARROW_WIDTH = 7;

        private Arrow(double positionX, double positionY, double destinationX, double destinationY) {
            this.line = new Line(positionX, positionY, destinationX, destinationY);

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

            this.getChildren().add(line);
            this.getChildren().add(arrow1);
            this.getChildren().add(arrow2);
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

    public class DragPoint extends Circle {
        private DragHandler dragHandler = new DragHandler(this);

        public DragPoint(DoubleProperty xProperty, DoubleProperty yProperty) {
            super(xProperty.get(), yProperty.get(), 5);
            this.getStyleClass().add("transition-drag-point");
            dragHandler.bindToPoint(this);
            dragHandler.bindToPoint(xProperty, yProperty);
        }

        public void setOnPressed(EventHandler<MouseEvent> handler) {
            this.setOnMousePressed(event -> {
                dragHandler.getOnPressed().handle(event);
                if (handler != null) {
                    handler.handle(event);
                }
            });
        }

        public void setOnDragged(EventHandler<MouseEvent> handler) {
            this.setOnMouseDragged(event -> {
                dragHandler.getOnDragged().handle(event);
                if (handler != null) {
                    handler.handle(event);
                }
            });
        }

        public DragHandler getDragHandler() {
            return dragHandler;
        }
    }
}
