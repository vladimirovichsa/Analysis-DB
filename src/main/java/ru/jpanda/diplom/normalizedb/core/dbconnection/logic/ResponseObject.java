package ru.jpanda.diplom.normalizedb.core.dbconnection.logic;

/**
 * Created by Alexey on 22.06.2017.
 */
public class ResponseObject {
    private String success;
    private String error;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
