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
    // 箭頭
    private Arrow arrow;

    // 拖曳點（尾端）
    private DragPoint p1;

    // 拖曳點（頭部）
    private DragPoint p2;

    // 處理拖曳行為
    private DragHandler rootDragHandler;

    public TransitionViewModel(Transition model) {
        super(model);
    }

    @Override
    public void draw(Store store) {
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

        store.draw(shape);
    }

    private void bindListeners(Store store) {
        rootDragHandler = new DragHandler(arrow);

        // 拖曳前儲存狀態
        p1.setOnPressed(event -> {
            store.saveHistory();
        });
        // 拖曳點移動時更新箭頭位置
        p1.setOnDragged(event -> {
            arrow.setStartX(model.getPositionX() - arrow.getTranslateX());
            arrow.setStartY(model.getPositionY() - arrow.getTranslateY());
        });

        p2.setOnPressed(event -> {
            store.saveHistory();
        });
        p2.setOnDragged(event -> {
            arrow.setEndX(model.getDestinationX() - arrow.getTranslateX());
            arrow.setEndY(model.getDestinationY() - arrow.getTranslateY());
        });


        // 拖曳箭頭時同時移動拖曳點
        arrow.setOnMousePressed(event -> {
            store.saveHistory();
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
            store.setSelected(model);
            event.consume();
        });

        store.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == model) {
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
    }
}
