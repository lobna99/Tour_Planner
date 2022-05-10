package com.example.tour_planner.dal;

import java.sql.SQLException;
import java.text.ParseException;
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

    List<T> getAll() throws SQLException;

    void create(T tr) throws SQLException, ParseException;

    void update(T t, List<?> params);

    void delete(T t) throws SQLException;
}