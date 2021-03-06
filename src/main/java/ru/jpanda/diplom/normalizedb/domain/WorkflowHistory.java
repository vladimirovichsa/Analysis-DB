package ru.jpanda.diplom.normalizedb.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Alexey on 20.03.2017.
 */
@Entity
@Table(name = "workflow_history")
@Proxy(lazy = false)
public class WorkflowHistory {
    
    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private Users userId;
    
    private String action;
    
    private String status;

//    @ManyToOne()
//    @JoinColumn(name = "serialization_id")
//    private Serialization serialization;

    private Date dateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users user) {
        this.userId = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStatus() {
        return status;
    }

//    public Serialization getSerialization() {
//        return serialization;
//    }

//    public void setSerialization(Serialization serialization) {
//        this.serialization = serialization;
//    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public Serialization getSerializationId() {
//        return serialization;
//    }
//
//    public void setSerializationId(Serialization serialization) {
//        this.serialization = serialization;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkflowHistory that = (WorkflowHistory) o;

        if (id != that.id) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (action != null ? !action.equals(that.action) : that.action != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        return dateTime != null ? dateTime.equals(that.dateTime) : that.dateTime == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }
}
