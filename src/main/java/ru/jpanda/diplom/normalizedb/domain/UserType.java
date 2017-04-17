package ru.jpanda.diplom.normalizedb.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * Created by Alexey on 28.03.2017.
 */
@Entity
@Table(name = "user_type")
@Proxy(lazy = false)
public class UserType {

    @Id
    @GenericGenerator(name = "kaugen", strategy = "increment")
    @GeneratedValue(generator = "kaugen")
    @Column(name = "type_id")
    private int type_id;

    @Column(name = "type")
    private String type = Type.USER.getUserType();

    public int getId() {
        return type_id;
    }

    public void setId(int id) {
        this.type_id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserType userUserType = (UserType) o;

        if (type_id != userUserType.type_id) return false;
        return type != null ? type.equals(userUserType.type) : userUserType.type == null;
    }

    @Override
    public int hashCode() {
        int result = type_id;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
enum Type {
    USER("USER"),
    ADMIN("ADMIN");

    String userType;

    private Type(String userType){
        this.userType = userType;
    }

    public String getUserType(){
        return userType;
    }
    private String type;

}