package diaed.view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by ucfan on 2017/5/25.
 */
public class EditorView extends View {
    private Stage primaryStage;

    private BorderPane root;

    // 畫布
    private Canvas canvas;

    // 工具列
    private Toolbar toolbar;

    public EditorView(Stage primaryStage) {
        this.root = new BorderPane();
	    this.primaryStage = primaryStage;

	    // 通常不會在建構子呼叫 create，只有這裡例外
	    create();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
        root.setCenter(canvas);
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
        root.setTop(toolbar);
    }

    @Override
    public void create() {
        try {
            Scene scene = new Scene(root,840,680);
            scene.getStylesheets().add(getClass().getResource("/editor/style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    };
}
