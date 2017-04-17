package ru.jpanda.diplom.normalizedb.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

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
    private User userId;
    
    private String action;
    
    private int status;

    @ManyToOne()
    @JoinColumn(name = "serialization_id")
    private Serialization serialization;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User user) {
        this.userId = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Serialization getSerializationId() {
        return serialization;
    }

    public void setSerializationId(Serialization serialization) {
        this.serialization = serialization;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkflowHistory that = (WorkflowHistory) o;

        if (id != that.id) return false;
        if (status != that.status) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (action != null ? !action.equals(that.action) : that.action != null) return false;
        return serialization != null ? serialization.equals(that.serialization) : that.serialization == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + status;
        result = 31 * result + (serialization != null ? serialization.hashCode() : 0);
        return result;
    }
}
