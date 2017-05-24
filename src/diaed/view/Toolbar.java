package diaed.view;

import diaed.Store;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

import java.io.IOException;

/**
 * Created by ucfan on 2017/3/27.
 */
public class Toolbar extends ToolBar {
    @FXML
    Button newButton;

    @FXML
    Button loadButton;

    @FXML
    Button saveButton;

    @FXML
    Button undoButton;

    @FXML
    Button redoButton;

    @FXML
    Button editButton;

    @FXML
    Button deleteButton;

    @FXML
    Button addStateButton;

    @FXML
    Button addTransitionButton;

    private Store store;

    public Toolbar(Store store) {
        this.store = store;

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/Toolbar.fxml")
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
        initEvents();
    }

    public void setOnNew(EventHandler<ActionEvent> event) {
        newButton.setOnAction(event);
    }

    public void setOnSave(EventHandler<ActionEvent> event) {
        saveButton.setOnAction(event);
    }

    public void setOnLoad(EventHandler<ActionEvent> event) {
        loadButton.setOnAction(event);
    }

    public void setOnUndo(EventHandler<ActionEvent> event) {
        undoButton.setOnAction(event);
    }

    public void setOnRedo(EventHandler<ActionEvent> event) {
        redoButton.setOnAction(event);
    }

    public void setOnEdit(EventHandler<ActionEvent> event) {
        editButton.setOnAction(event);
    }

    public void setOnDelete(EventHandler<ActionEvent> event) {
        deleteButton.setOnAction(event);
    }

    public void setOnAddState(EventHandler<ActionEvent> event) {
        addStateButton.setOnAction(event);
    }

    public void setOnAddTranstion(EventHandler<ActionEvent> event) {
        addTransitionButton.setOnAction(event);
    }


    public void initEvents() {
        // 開新檔案（就是清空啦）
        setOnNew(event -> store.newDiagram());

        // 讀檔（讀取預先定義的範例）
        setOnLoad(event -> store.loadDiagram());

        // 復原
        setOnUndo(event -> store.undo());

        // 還原
        setOnRedo(event -> store.redo());

        setOnEdit(event -> store.editElement());

        // 刪除
        setOnDelete(event -> store.deleteElement());

        // 在畫面上新增 state
        setOnAddState(event -> store.addState());

        // 在畫面上新增 transition
        setOnAddTranstion(event -> store.addTransition());
    }

}

