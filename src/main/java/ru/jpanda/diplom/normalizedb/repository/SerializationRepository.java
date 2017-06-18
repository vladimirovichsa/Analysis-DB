package ru.jpanda.diplom.normalizedb.repository;

import org.springframework.data.repository.CrudRepository;
import ru.jpanda.diplom.normalizedb.domain.Serialization;
import ru.jpanda.diplom.normalizedb.domain.UserType;

import java.util.List;

/**
 * Created by Alexey on 27.03.2017.
 */
public interface SerializationRepository extends CrudRepository<Serialization, Integer> {

    Serialization findById(int id);
    Serialization save(Serialization serialization);
    List<Serialization> findAll();
}
