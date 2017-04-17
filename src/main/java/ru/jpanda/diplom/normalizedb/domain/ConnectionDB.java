package ru.jpanda.diplom.normalizedb.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Alexey on 02.04.2017.
 */
@Entity
@Table(name = "connection_db")
@Proxy(lazy = false)
public class ConnectionDB implements Serializable{

    @Id
    @GenericGenerator(name = "kaugen", strategy = "increment")
    @GeneratedValue(generator = "kaugen")
    @Column(name = "id")
    private int id;

    private String url;

    @NotEmpty(message = "Пожалуйтса введите хост")
    private String host;

    @NotNull(message = "Пожалуйтса введите порт")
    @Column(name = "port",nullable = false)
    private Integer port;

    @NotEmpty(message = "Пожалуйтса введите имя базы данных")
    private String data_base;

    @NotEmpty(message = "Пожалуйтса введите имя пользователя")
    private String user_name;

    @NotEmpty(message = "Пожалуйтса введите пароль")
    private String password;

    @OneToOne()
    @JoinColumn(name = "user_id")
    private User userId;

    @OneToOne()
    @JoinColumn(name = "table_type_id")
    private TableType tableTypeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getData_base() {
        return data_base;
    }

    public void setData_base(String data_base) {
        this.data_base = data_base;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public TableType getTable_type_id() {
        return tableTypeId;
    }

    public void setTable_type_id(TableType table_type_id) {
        this.tableTypeId = table_type_id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConnectionDB that = (ConnectionDB) o;

        if (id != that.id) return false;
        if (port != that.port) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (host != null ? !host.equals(that.host) : that.host != null) return false;
        if (data_base != null ? !data_base.equals(that.data_base) : that.data_base != null) return false;
        if (user_name != null ? !user_name.equals(that.user_name) : that.user_name != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        return tableTypeId != null ? tableTypeId.equals(that.tableTypeId) : that.tableTypeId == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (host != null ? host.hashCode() : 0);
        result = 31 * result + port;
        result = 31 * result + (data_base != null ? data_base.hashCode() : 0);
        result = 31 * result + (user_name != null ? user_name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (tableTypeId != null ? tableTypeId.hashCode() : 0);
        return result;
    }
}

