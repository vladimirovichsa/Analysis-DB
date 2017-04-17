package ru.jpanda.diplom.normalizedb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jpanda.diplom.normalizedb.domain.TableType;
import ru.jpanda.diplom.normalizedb.repository.TableTypeRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Alexey on 02.04.2017.
 */

@Service
@Transactional
public class TableTypeService {

    @Autowired
    private TableTypeRepository tableTypeRepository;

    public TableType findByTableTypeId(int tableTypeId) {
        return this.tableTypeRepository.findByTableTypeId(tableTypeId);
    }

    public void delete(TableType connection) {
        this.tableTypeRepository.delete(connection);
    }

    public List<TableType> getAllTabletype() {
        return this.tableTypeRepository.findAll();
    }

    public void addTableType(TableType tableType) {
        this.tableTypeRepository.save(tableType);
    }
}
