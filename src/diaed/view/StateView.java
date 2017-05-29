package diaed.view;

import diaed.model.State;
import javafx.scene.shape.Circle;

/**
 * Created by ucfan on 2017/5/24.
 */

public class StateView extends View {
    // 圓圈部分
    private Circle circle;

    // 文字部分
    private EditableText text;

    private State model;

    public StateView() {}

    public StateView(State model) {
        this.model = model;
        // 繪製圓圈
    }

    @Override
    public void create() {
        System.out.println("create");
        circle = new Circle(model.getPositionX(), model.getPositionY(), 60);
        circle.getStyleClass().add("state-circle");

        // 繪製輸入匡
        text = new EditableText(model.getPositionX(), model.getPositionY());

        // 群組
        this.getChildren().add(circle);
        this.getChildren().add(text);
    }

    public Circle getCircle() {
        return circle;
    }

    public EditableText getText() {
        return text;
    }

}
