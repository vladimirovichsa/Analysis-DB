package ru.jpanda.diplom.normalizedb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jpanda.diplom.normalizedb.domain.Users;
import ru.jpanda.diplom.normalizedb.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Alexey on 27.03.2017.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Users getUserById(int id){
        return userRepository.findById(id);
    };

    public Users getUserByLogin(String login){
        return userRepository.findByLogin(login);
    }

    public Users addUser(Users user){
        return userRepository.save(user);
    }

    public List<Users> getUsers(){
        return userRepository.findAll();
    }
}
