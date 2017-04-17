package ru.jpanda.diplom.normalizedb.repository;

import org.springframework.data.repository.CrudRepository;
import ru.jpanda.diplom.normalizedb.domain.User;

import java.util.List;

/**
 * Created by Alexey on 27.03.2017.
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    User findById(int id);
    User findByLogin(String login);
    User save(User user);
    List<User> findAll();
}
