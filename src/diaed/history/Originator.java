package diaed.history;

/**
 * 實作此介面的 Originator 擁有以 Memento 為單位做存檔及讀檔的能力
 * 目前 StateDiagram 有實作此介面
 */
public interface Originator {
    Memento save();
    void restore(Memento backup);
}
