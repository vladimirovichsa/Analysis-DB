package ru.jpanda.diplom.normalizedb.core.dbconnection.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexey on 25.06.2017.
 */
public class Insert {
    private String tableName;
    private List<Attribute> fields = new ArrayList<>();
    private List<String> value = new ArrayList<>();
    private Map<Integer,List<String>> divValue = new HashMap<>();

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Attribute> getFields() {
        return fields;
    }

    public void setFields(List<Attribute> fields) {
        this.fields = fields;
    }
    public void setFields(Attribute field) {
        this.fields.add(field);
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }

    public void setValue(String value) {
        this.value.add(value);
    }

    public Map<Integer, List<String>> getDivValue() {
        return divValue;
    }

    public void setDivValue(Map<Integer, List<String>> divValue) {
        this.divValue = divValue;
    }

    public void setDivValue(Integer attrIndex, String divValue) {
        List<String> value = this.divValue.get(attrIndex);
        value.add(divValue);
        this.divValue.put(attrIndex, value);
    }
}
