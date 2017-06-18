package ru.jpanda.diplom.normalizedb.repository;

import org.springframework.data.repository.CrudRepository;
import ru.jpanda.diplom.normalizedb.domain.UserType;
import ru.jpanda.diplom.normalizedb.domain.Users;

import java.util.List;

/**
 * Created by Alexey on 27.03.2017.
 */
public interface UserTypeRepository extends CrudRepository<UserType, Integer> {

    UserType findById(int id);
    UserType save(UserType userType);
    List<UserType> findAll();
}
