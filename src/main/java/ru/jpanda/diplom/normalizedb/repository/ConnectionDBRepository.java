package ru.jpanda.diplom.normalizedb.repository;

import org.springframework.data.repository.CrudRepository;
import ru.jpanda.diplom.normalizedb.domain.ConnectionDB;
import ru.jpanda.diplom.normalizedb.domain.User;

import java.util.List;

/**
 * Created by Alexey on 27.03.2017.
 */
public interface ConnectionDBRepository extends CrudRepository<ConnectionDB, Integer> {

    ConnectionDB findById(int id);
    ConnectionDB save(ConnectionDB connection);
    void delete(ConnectionDB connection);
    List<ConnectionDB> findAll();
    List<ConnectionDB> findByUserId(User user_id);
    ConnectionDB findByIdAndUserId(int id, User user_id);
}
