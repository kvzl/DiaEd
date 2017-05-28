package diaed;

import diaed.builder.DiagramTemplate;
import diaed.builder.Template1;
import diaed.command.*;
import diaed.history.EditHistory;
import diaed.model.DiagramElement;
import diaed.model.StateDiagram;
import diaed.scene.EditorScene;
import diaed.scene.StartupScene;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.stage.Stage;


/**
 * Created by ucfan on 2017/3/31.
 */


/* 一個 Mediator 的概念 */

public class Store {
    private EditorScene root;

    // 狀態圖 data
    private ObjectProperty<StateDiagram> diagram = new SimpleObjectProperty<>();

    // 紀錄目前選取的元素
    private ObjectProperty<DiagramElement> selectedElement = new SimpleObjectProperty<>(null);

    // 紀錄目前正在編輯的元素
    private ObjectProperty<DiagramElement> editing = new SimpleObjectProperty<>();

    // 紀錄目前選取的範本
    private ObjectProperty<DiagramTemplate> selectedTemplate = new SimpleObjectProperty<>(null);


    // 編輯紀錄
    // 用來 undo/redo
    private EditHistory histories = new EditHistory();

    private Stage primaryStage;

    public Store(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    /* getters 和 setters */

    public StateDiagram getDiagram() {
        return diagram.get();
    }

    public void setRoot(EditorScene root) {
        this.root = root;
    }

    public void setSelectedElement(DiagramElement selectedElement) {
        this.selectedElement.set(selectedElement);
    }

    public DiagramElement getSelectedElement() {
        return this.selectedElement.get();
    }

    public DiagramElement getEditing() {
        return this.editing.get();
    }

    public void setEditing(DiagramElement editing) {
        saveHistory();
        this.editing.set(editing);
    }

    public ObjectProperty<DiagramElement> editingProperty() {
        return editing;
    }

    public DiagramTemplate getSelectedTemplate() {
        return selectedTemplate.get();
    }

    public ObjectProperty<DiagramElement> selectedElementProperty() {
        return this.selectedElement;
    }

    public ObjectProperty<DiagramTemplate> selectedTemplateProperty() {
        return selectedTemplate;
    }

    public void setSelectedTemplate(DiagramTemplate selectedTemplate) {
        this.selectedTemplate.set(selectedTemplate);
    }


    // 繪製（將元件加入到畫布中）
    public void draw(Node node) {
        root.add(node);
    }

    // 將目前狀態存入編輯紀錄
    // 進行變更前呼叫此方法，之後可 redo
    public void saveHistory() {
        histories.invoke(new SaveHistoryCommand(this));
    }

    public void setDiagram(StateDiagram diagram) {
        root.setModel(diagram);
        this.diagram = root.modelProperty();
    }

    public void resetHistory() {
        histories.reset();
    }


    // 復原
    public void undo() {
        if (histories.undoable()) {
            histories.undo();
        }
    }

    // 還原
    public void redo() {
        if (histories.redoable()) {
            histories.redo();
        }
    }

    // 編輯選取的元素
    public void editElement() {
        histories.invoke(new EditElementCommand(this));
    }

    // 刪除選取的元素
    public void deleteElement() {
        histories.invoke(new DeleteElementCommand(this));
    }

    // 在畫面上新增 state
    public void addState() {
        histories.invoke(new AddStateCommand(this));
    }

    // 在畫面上新增 transition
    public void addTransition() {
        histories.invoke(new AddTransitionCommand(this));
    }


    // 範本資料
    public StateDiagram getTemplateDiagram() {
        DiagramTemplate template = new Template1();
        return template.getDiagram();
    }

    public void gotoEditor() {
        EditorScene vm = new EditorScene(this, primaryStage);
        vm.initialize();
    }

    public void gotoStartup() {
        new StartupScene(this, primaryStage);
    }
}

