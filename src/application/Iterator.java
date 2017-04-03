package application;

/**
 * Created by ucfan on 2017/3/28.
 */

public interface Iterator<T> {
    boolean hasNext();
    T next();
    void remove();
}
