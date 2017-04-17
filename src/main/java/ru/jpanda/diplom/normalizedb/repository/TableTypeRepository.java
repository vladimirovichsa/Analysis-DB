package ru.jpanda.diplom.normalizedb.repository;

import org.springframework.data.repository.CrudRepository;
import ru.jpanda.diplom.normalizedb.domain.TableType;

import java.util.List;

/**
 * Created by Alexey on 27.03.2017.
 */
public interface TableTypeRepository extends CrudRepository<TableType, Integer> {

    TableType findByTableTypeId(int tableTypeId);
    TableType save(TableType tableType);
    void delete(TableType tableType);
    List<TableType> findAll();
}
