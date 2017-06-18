package ru.jpanda.diplom.normalizedb.repository;

import org.springframework.data.repository.CrudRepository;
import ru.jpanda.diplom.normalizedb.domain.Serialization;
import ru.jpanda.diplom.normalizedb.domain.Users;
import ru.jpanda.diplom.normalizedb.domain.WorkflowHistory;

import java.util.List;

/**
 * Created by Alexey on 27.03.2017.
 */
public interface WorkFlowHistoryRepository extends CrudRepository<WorkflowHistory, Integer> {

    WorkflowHistory findById(int id);
    WorkflowHistory findByUserId(Users users);
    WorkflowHistory save(WorkflowHistory workflowHistory);
    List<WorkflowHistory> findAll();
}
