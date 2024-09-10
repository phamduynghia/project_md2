package feature;

import java.util.List;

public interface IBase<T, E> {
    List<T> getAll();

    void save(T element);

    void delete(E id);

    T findById(E id);

    E getNewId();
}