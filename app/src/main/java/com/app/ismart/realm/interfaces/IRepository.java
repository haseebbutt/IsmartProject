package com.app.ismart.realm.interfaces;


import java.util.List;

/**
 * Created by cielowigle on 21/02/2017.
 */

public interface IRepository <T> {
    long add(T item);

    void add(Iterable<T> items);

    void update(T item);

    void remove(T item);

    void remove(Specification specification);
    void removeAll();
    List<T> query(Specification specification);

}
