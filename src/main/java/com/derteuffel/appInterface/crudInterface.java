package com.derteuffel.appInterface;

import java.util.List;

/**
 * Created by derteuffel on 01/11/2018.
 */
public interface crudInterface<T> {
    List<?> listAll();

    T getById(Long id);

    T saveOrUpdate(T domainObject);

    void delete(Long id);
}
