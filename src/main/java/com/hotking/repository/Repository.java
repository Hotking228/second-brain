package com.hotking.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public interface Repository<E, K extends Serializable> {

    List<E> findAll(Map<String, Object> properties);

    E save(E entity);

    void delete(E entity);

    void update(E entity);

    Optional<E> findById(K id);
}
