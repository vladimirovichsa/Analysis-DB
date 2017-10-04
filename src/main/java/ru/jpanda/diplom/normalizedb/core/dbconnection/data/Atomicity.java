package ru.jpanda.diplom.normalizedb.core.dbconnection.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey on 23.06.2017.
 */
public class Atomicity {
    private List<Integer> row = new ArrayList<>();

    public List<Integer> getRow() {
        return row;
    }

    public void setRow(List<Integer> row) {
        this.row = row;
    }
}
