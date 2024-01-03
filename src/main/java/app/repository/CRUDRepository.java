package app.repository;

import java.util.List;

public interface CRUDRepository<T, IdType> {

    T save(T entity);

    T update(T entity);

    T findById(IdType id);

    List<T> findAll();

    boolean delete(T entity);
}
