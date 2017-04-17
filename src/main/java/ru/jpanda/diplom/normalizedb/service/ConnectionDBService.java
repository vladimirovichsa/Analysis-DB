package ru.jpanda.diplom.normalizedb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jpanda.diplom.normalizedb.domain.ConnectionDB;
import ru.jpanda.diplom.normalizedb.domain.User;
import ru.jpanda.diplom.normalizedb.repository.ConnectionDBRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Alexey on 02.04.2017.
 */

@Service
@Transactional
public class ConnectionDBService{

    @Autowired
    private ConnectionDBRepository connectionDBRepository;

    public ConnectionDB findById(int id) {
        return this.connectionDBRepository.findById(id);
    }

    public ConnectionDB getConnectionByIdAndUserId(int id , User user_id) {
        return this.connectionDBRepository.findByIdAndUserId(id,user_id);
    }

    public void delete(ConnectionDB connection) {
        this.connectionDBRepository.delete(connection);
    }

    public List<ConnectionDB> getAllConnection() {
        return this.connectionDBRepository.findAll();
    }

    public List<ConnectionDB> getAllConnectionByUserID(User userId) {
        return this.connectionDBRepository.findByUserId(userId);
    }

    public void addConnectionDB(ConnectionDB connectionDB) {
        this.connectionDBRepository.save(connectionDB);
    }
}
