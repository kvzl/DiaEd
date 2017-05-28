package diaed.scene;

import diaed.Store;
import diaed.builder.*;
import diaed.view.TemplateView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StartupScene extends BorderPane {
    private Stage primaryStage;
    private Store store;

    private Scene scene;

    private Button loadButton;
    private Button exitButton;
    private Button createButton;


    public StartupScene(Store store, Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.store = store;
        create();
        bindListeners();
    }


    private void create() {
        try {
            BorderPane root = FXMLLoader.load(getClass().getResource("/startup/index.fxml"));

            scene = new Scene(root, 640, 480);
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();

            loadButton = (Button) scene.lookup("#loadButton");
            exitButton = (Button) scene.lookup("#exitButton");
            createButton = (Button) scene.lookup("#createButton");


        } catch(Exception e) {
            e.printStackTrace();
        }

        GridPane grid = (GridPane) scene.lookup("#grid");

        grid.addRow(0,
                new TemplateView(store, new BlankTemplate()),
                new TemplateView(store, new Template1()),
                new TemplateView(store, new Template2())
        );
        grid.addRow(1,
                new TemplateView(store, new Template3()),
                new TemplateView(store, new Template4()),
                new TemplateView(store, new Template5())
        );

    }

    private void bindListeners() {
        loadButton.setOnAction(event -> {
            store.loadDiagram();
        });

        // 離開程式
        exitButton.setOnAction(event -> {
            System.exit(0);
        });

        // 點擊 create button 時進入編輯畫面
        createButton.setOnAction(event -> {
            store.gotoEditor();
        });

        // 未選取範本時無法點擊 create button
        store.selectedTemplateProperty().addListener(((observable, oldValue, newValue) -> {
            createButton.setDisable(newValue == null);
        }));
    }
}
