package diaed.view;

import diaed.Store;
import diaed.builder.DiagramTemplate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by ucfan on 2017/5/28.
 */


public class TemplateView extends VBox {
    @FXML
    Label templateName;

    private DiagramTemplate template;
    private Store store;


    public TemplateView(Store store, DiagramTemplate template) {
        this.store = store;
        this.template = template;

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/startup/template.fxml")
        );

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    @FXML
    private void initialize() {
        // 設定樣本名稱
        templateName.setText(template.getName());

        // 點選範本
        setOnMouseClicked(event -> {
            DiagramTemplate selected = store.selectedTemplateProperty().get();
            store.setSelectedTemplate((selected != template) ? template : null);
        });

        // 當點選該樣本時改變樣式
        store.selectedTemplateProperty().addListener(((observable, oldValue, newValue) -> {
            if (store.selectedTemplateProperty().get() == template) {
                setSelected(true);
            }
            else {
                setSelected(false);
            }
        }));

    }

    private void setSelected(boolean selected) {
        if (selected) {
            setStyle("-fx-border-style: solid; -fx-border-width: 1; -fx-border-color: gray;");
        }
        else {
            setStyle("-fx-border-style: none; -fx-border-width: 0; -fx-border-color: white;");
        }
    }
}
