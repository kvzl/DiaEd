package application;

import application.Iterator;

/**
 * Created by ucfan on 2017/4/3.
 */
public interface Iterable<T> {
    Iterator<T> iterator();
}
