package ru.jpanda.diplom.normalizedb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jpanda.diplom.normalizedb.domain.Serialization;
import ru.jpanda.diplom.normalizedb.domain.Users;
import ru.jpanda.diplom.normalizedb.domain.WorkflowHistory;
import ru.jpanda.diplom.normalizedb.repository.WorkFlowHistoryRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Alexey on 27.03.2017.
 */
@Service
@Transactional
public class WorkFlowHistoryService {

    @Autowired
    private WorkFlowHistoryRepository workFlowHistoryRepository;

    public WorkflowHistory getSerialization(int id){
        return workFlowHistoryRepository.findById(id);
    };

    public WorkflowHistory addSerialization(WorkflowHistory workflowHistory){
        return workFlowHistoryRepository.save(workflowHistory);
    }

    public WorkflowHistory getHistoryByUser(Users users){
        return workFlowHistoryRepository.findByUserId(users);
    }

    public List<WorkflowHistory> getSerialization(){
        return workFlowHistoryRepository.findAll();
    }
}
