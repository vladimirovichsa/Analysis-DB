package ru.jpanda.diplom.normalizedb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jpanda.diplom.normalizedb.domain.Serialization;
import ru.jpanda.diplom.normalizedb.domain.UserType;
import ru.jpanda.diplom.normalizedb.repository.SerializationRepository;
import ru.jpanda.diplom.normalizedb.repository.UserTypeRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Alexey on 27.03.2017.
 */
@Service
@Transactional
public class SerializationService {

    @Autowired
    private SerializationRepository serializationRepository;

    public Serialization getSerialization(int id){
        return serializationRepository.findById(id);
    };

    public Serialization addSerialization(Serialization serialization){
        return serializationRepository.save(serialization);
    }

    public List<Serialization> getSerialization(){
        return serializationRepository.findAll();
    }
}
