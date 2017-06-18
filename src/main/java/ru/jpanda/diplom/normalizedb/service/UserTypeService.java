package ru.jpanda.diplom.normalizedb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jpanda.diplom.normalizedb.domain.UserType;
import ru.jpanda.diplom.normalizedb.domain.Users;
import ru.jpanda.diplom.normalizedb.repository.UserRepository;
import ru.jpanda.diplom.normalizedb.repository.UserTypeRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Alexey on 27.03.2017.
 */
@Service
@Transactional
public class UserTypeService {

    @Autowired
    private UserTypeRepository userTypeRepository;

    public UserType getUserType(int id){
        return userTypeRepository.findById(id);
    };

    public UserType addUserType(UserType userType){
        return userTypeRepository.save(userType);
    }

    public List<UserType> getUserType(){
        return userTypeRepository.findAll();
    }
}
