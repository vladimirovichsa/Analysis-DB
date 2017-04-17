package ru.jpanda.diplom.normalizedb.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * Created by Alexey on 20.03.2017.
 */
@Entity
@Table(name = "serialization")
@Proxy(lazy = false)
public class Serialization {
    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    @Column(name = "id")
    private int id;

    private String serializationShemaPath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerializationShemaPath() {
        return serializationShemaPath;
    }

    public void setSerializationShemaPath(String serializationShemaPath) {
        this.serializationShemaPath = serializationShemaPath;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Serialization that = (Serialization) o;

        if (id != that.id) return false;
        return serializationShemaPath != null ? serializationShemaPath.equals(that.serializationShemaPath) : that.serializationShemaPath == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (serializationShemaPath != null ? serializationShemaPath.hashCode() : 0);
        return result;
    }
}
