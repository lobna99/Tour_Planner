package com.example.tour_planner.dal;

import java.util.List;
import java.util.Optional;

/**
 * Dao interface defines an abstract API that performs CRUD operations on objects of type T
 * see: https://www.baeldung.com/java-dao-pattern
 *
 * @param <T> the type of the model
 */
public interface Dao<T> {

    Optional<T> get(int id);

    List<T> getAll();

    void create(T tr);

    void update(T t, List<?> params);

    void delete(T t);
}