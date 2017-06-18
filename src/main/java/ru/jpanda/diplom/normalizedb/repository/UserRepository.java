package ru.jpanda.diplom.normalizedb.repository;

import org.springframework.data.repository.CrudRepository;
import ru.jpanda.diplom.normalizedb.domain.Users;

import java.util.List;

/**
 * Created by Alexey on 27.03.2017.
 */
public interface UserRepository extends CrudRepository<Users, Integer> {

    Users findById(int id);
    Users findByLogin(String login);
    Users save(Users user);

    List<Users> findAll();
}
