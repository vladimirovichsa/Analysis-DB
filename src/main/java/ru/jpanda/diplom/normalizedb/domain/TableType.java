package ru.jpanda.diplom.normalizedb.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;
import ru.jpanda.diplom.normalizedb.core.dbconnection.data.dBTypes.TypeEnum;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Alexey on 03.04.2017.
 */

@Entity
@Table(name = "table_type")
@Proxy(lazy = false)
public class TableType implements Serializable{

    @Id
    @GenericGenerator(name = "kaugen", strategy = "increment")
    @GeneratedValue(generator = "kaugen")
    @Column(name = "table_type_id")
    private int tableTypeId;

    @Enumerated(EnumType.STRING)
    private TypeEnum type = TypeEnum.MYSQL;

    public int getTableTypeId() {
        return tableTypeId;
    }

    public void setTableTypeId(int tableTypeId) {
        this.tableTypeId = tableTypeId;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TableType tableType = (TableType) o;

        if (tableTypeId != tableType.tableTypeId) return false;
        return type != null ? type.equals(tableType.type) : tableType.type == null;
    }

    @Override
    public int hashCode() {
        int result = tableTypeId;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}