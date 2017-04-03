package application.history;

/**
 * Created by ucfan on 2017/4/4.
 */
public interface Originator<T extends DiagramState> {
    T save();
    void restore(T backup);
}
