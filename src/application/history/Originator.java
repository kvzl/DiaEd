package application.history;

public interface Originator {
    Memento save();
    void restore(Memento backup);
}
