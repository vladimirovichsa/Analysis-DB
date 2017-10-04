package ru.jpanda.diplom.normalizedb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public WorkflowHistory getHistory(int id){
        return workFlowHistoryRepository.findById(id);
    };

    public WorkflowHistory addHistory(WorkflowHistory workflowHistory){
        return workFlowHistoryRepository.save(workflowHistory);
    }

    public List<WorkflowHistory> getHistoryByUser(Users users){
        return workFlowHistoryRepository.findAllByUserId(users);
    }

    public List<WorkflowHistory> getHistory(){
        return workFlowHistoryRepository.findAll();
    }
}
