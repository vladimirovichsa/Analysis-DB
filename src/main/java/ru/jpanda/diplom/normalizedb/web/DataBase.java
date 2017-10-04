package ru.jpanda.diplom.normalizedb.web;

import netscape.javascript.JSObject;
import org.json.JSONString;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.jpanda.diplom.normalizedb.core.dbconnection.data.CreateTable;
import ru.jpanda.diplom.normalizedb.core.dbconnection.data.Attribute;
import ru.jpanda.diplom.normalizedb.core.dbconnection.data.Database;
import ru.jpanda.diplom.normalizedb.core.dbconnection.data.RelationSchema;
import ru.jpanda.diplom.normalizedb.core.dbconnection.dbConnection.DbConnection;
import ru.jpanda.diplom.normalizedb.core.dbconnection.logic.ResponseObject;
import ru.jpanda.diplom.normalizedb.domain.ConnectionDB;
import ru.jpanda.diplom.normalizedb.domain.TableType;
import ru.jpanda.diplom.normalizedb.domain.Users;
import ru.jpanda.diplom.normalizedb.domain.WorkflowHistory;
import ru.jpanda.diplom.normalizedb.service.*;
import ru.jpanda.diplom.normalizedb.service.analysis.AnalysisDB;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Alexey on 30.01.2017.
 */

@Controller
public class DataBase {
    @Autowired
    private UserService userService;

    @Autowired
    private ConnectionDBService connectionDBService;

    @Autowired
    private TableTypeService tableTypeService;

    @Autowired
    private DataBaseService dataBaseService;

    @Autowired
    private WorkFlowHistoryService workFlowHistoryService;


    @RequestMapping(value = "/connections", method = RequestMethod.GET,
            produces = "application/json")
    @PreAuthorize(value = "!isAnonymous()")
    public String getConnectionsView(Model model) {
        Users user = userService.getUserByLogin(getPrincipal());
        model.addAttribute("connectionForm", new ConnectionDB());
        model.addAttribute("connectionAll", connectionDBService.getAllConnectionByUserID(user));
        model.addAttribute("tableType", tableTypeService.getAllTabletype());
        return "connectionPage";
    }

    @RequestMapping(value = "/connections/connection/{connectionId}", method = RequestMethod.GET,
            produces = "application/json")
    @PreAuthorize(value = "!isAnonymous()")
    public @ResponseBody
    ConnectionDB getConnection(@PathVariable(value = "connectionId") int connectionId, Model model) {
        return connectionDBService.getConnectionById(connectionId);
    }

    @RequestMapping(value = "/connection/databasetype", method = RequestMethod.GET,
            produces = "application/json")
    @PreAuthorize(value = "!isAnonymous()")
    public @ResponseBody
    List<TableType> getAllDataBaseType(Model model) {
        return tableTypeService.getAllTabletype();
    }

    @RequestMapping(value = "/connections", method = RequestMethod.POST,
            produces = "application/json")
    @PreAuthorize(value = "!isAnonymous()")
    public String addConnections(
            @RequestParam int tableTypeId,
            @Valid ConnectionDB connectionDB, BindingResult bindingResult) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        TableType byTableTypeId = tableTypeService.findByTableTypeId(tableTypeId);
        Users user = userService.getUserByLogin(getPrincipal());
        ConnectionDB con = new ConnectionDB();
        con.setTable_type_id(byTableTypeId);
        con.setUserId(user);
        con.setData_base(connectionDB.getData_base());
        con.setHost(connectionDB.getHost());
        con.setPassword(connectionDB.getPassword());
        con.setPort(connectionDB.getPort());
        con.setUrl(connectionDB.getUrl());
        con.setUser_name(connectionDB.getUser_name());

        connectionDBService.addConnectionDB(con);
        return "redirect:/connections";
    }

    @RequestMapping(value = "/database/createdatabase", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "!isAnonymous()")
    public @ResponseBody
    ResponseObject createDataBase(@RequestBody CreateTable obj, BindingResult bindingResult) {
        ArrayList<Attribute> listAttribute = obj.getListAttribute();

        return dataBaseService.getConnection().createTable(obj.getTableName(), listAttribute);

    }

    @RequestMapping(value = "/database/copydata", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "!isAnonymous()")
    public @ResponseBody
    ResponseObject copyData(@RequestBody CreateTable obj, BindingResult bindingResult) {
        ArrayList<Attribute> listOldAttribute = obj.getListOldAttribute();
        ArrayList<Attribute> listAttribute = obj.getListAttribute();
        ResponseObject object = dataBaseService.getConnection().moveTableData(obj.getTableNameOld(),obj.getTableName(), listAttribute,listOldAttribute);
        if(null != object.getSuccess()){
            if(null != dataBaseService.getConnection().addColumn(obj.getTableNameOld(),obj.getTableName()).getSuccess()){
                object = dataBaseService.getConnection().copyDataTableToInDataTableFrom(obj.getTableNameOld(),obj.getTableName(), listAttribute,listOldAttribute);
            }
        }
        return object;

    }

    @RequestMapping(value = "/database/deletedata", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "!isAnonymous()")
    public @ResponseBody
    ResponseObject deleteData(@RequestBody CreateTable obj, BindingResult bindingResult) {
        ArrayList<Attribute> listOldAttribute = obj.getListOldAttribute();
        ArrayList<Attribute> listAttribute = obj.getListAttribute();
        ResponseObject object = dataBaseService.getConnection().deleteOldColumn(obj.getTableNameOld(),listOldAttribute);

        return object;

    }

    @RequestMapping(value = "/connection/update", method = RequestMethod.POST,
            produces = "application/json")
    @PreAuthorize(value = "!isAnonymous()")
    public void updateConnection(
            @RequestParam int tableTypeId,
            @RequestParam String dataBase,
            @RequestParam String host,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam int port,
            @RequestParam String url,
            BindingResult bindingResult) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        TableType byTableTypeId = tableTypeService.findByTableTypeId(tableTypeId);
        Users user = userService.getUserByLogin(getPrincipal());
        ConnectionDB con = new ConnectionDB();
        con.setTable_type_id(byTableTypeId);
        con.setUserId(user);
        con.setData_base(dataBase);
        con.setHost(host);
        con.setPassword(password);
        con.setPort(port);
        con.setUrl(url);
        con.setUser_name(username);

        connectionDBService.addConnectionDB(con);
        WorkflowHistory workflowHistory = new WorkflowHistory();
        workflowHistory.setAction("Редактирование подключения к базе данных");
        workflowHistory.setStatus("Выполнено");
        workflowHistory.setDateTime(new Date());
        workflowHistory.setUserId(userService.getUserByLogin(getPrincipal()));
        workFlowHistoryService.addHistory(workflowHistory);
    }

    @RequestMapping(value = {"/database"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize(value = "!isAnonymous()")
    public String getDataBase(Model model) {
        model.addAttribute("connectionDB", new ConnectionDB());
        model.addAttribute("connectionList", connectionDBService
                .getAllConnectionByUserID(userService.getUserByLogin(getPrincipal())));
        return "dataBasePage";
    }

    @RequestMapping(value = "/database", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize(value = "!isAnonymous()")
    public String getDataBaseByID(
            @RequestParam int connection) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        int d = connection;
        ConnectionDB connectionDB = connectionDBService
                .getConnectionByIdAndUserId(connection, userService.getUserByLogin(getPrincipal()));
        if (connectionDB == null) {
            return "redirect:/database";
        }
        return "redirect:/database/" + d;
    }

    @RequestMapping(value = {"/database/userconnection"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize(value = "!isAnonymous()")
    public
    @ResponseBody
    List<ConnectionDB> getUserConnection() {
        return connectionDBService
                .getAllConnectionByUserID(userService.getUserByLogin(getPrincipal()));

    }

    @RequestMapping(value = "/database/{connectionId}", method = RequestMethod.GET,
            produces = "application/json")
    public String getRelationDatabaseByID(@PathVariable(value = "connectionId") int connectionId, Model model) throws SQLException {
        ConnectionDB connectionDB = connectionDBService
                .getConnectionByIdAndUserId(connectionId, userService.getUserByLogin(getPrincipal()));


        DbConnection dbConnection = dataBaseService.connectionDataBase(connectionDB);

        Database database = dbConnection.getDatabase();
        int i = 0;

        model.addAttribute("database", database);
        model.addAttribute("connectionDB", new ConnectionDB());
        model.addAttribute("connectionList", connectionDBService
                .getAllConnectionByUserID(userService.getUserByLogin(getPrincipal())));
        WorkflowHistory workflowHistory = new WorkflowHistory();
        workflowHistory.setAction("Подключение к " + database.getType().name);
        workflowHistory.setStatus("Выполнено");
        workflowHistory.setDateTime(new Date());
        workflowHistory.setUserId(userService.getUserByLogin(getPrincipal()));
        workFlowHistoryService.addHistory(workflowHistory);
        return "dataBasePage";
    }

    @RequestMapping(value = {"/database/table/{schemaName}"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize(value = "!isAnonymous()")
    public
    @ResponseBody
    RelationSchema getTableByTableName(@PathVariable(value = "schemaName") String schemaName) throws SQLException {
        dataBaseService.getConnection().getDataByTableNameFromDb(schemaName);
        RelationSchema relation = dataBaseService.getConnection().getDatabase().getRelationSchema(schemaName);
        WorkflowHistory workflowHistory = new WorkflowHistory();
        workflowHistory.setAction("Получение данных из таблицы " + schemaName);
        workflowHistory.setStatus("Выполнено");
        workflowHistory.setDateTime(new Date());
        workflowHistory.setUserId(userService.getUserByLogin(getPrincipal()));
        workFlowHistoryService.addHistory(workflowHistory);
        return relation;
    }

    @RequestMapping(value = {"/database/table/analysis/{schemaName}/{tableName}/atomicity"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize(value = "!isAnonymous()")
    public
    @ResponseBody
    RelationSchema deleteAtomicy(@PathVariable(value = "schemaName") String schemaName,
                                 @PathVariable(value = "tableName") String tableName) throws SQLException {
        RelationSchema relation = dataBaseService.getConnection().getDatabase().getRelationSchema(schemaName);
        ArrayList<Attribute> attributes = relation.getAttributes();
        for (Attribute attribute : attributes) {
            if(attribute.isAtomicity()){
                dataBaseService.getConnection().addRow(tableName,attributes,attribute.getAtomicityObject().getRow());
            }
        }
        return relation;
    }

    @RequestMapping(value = {"/database/table/analysis/{schemaName}"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize(value = "!isAnonymous()")
    public
    @ResponseBody
    RelationSchema getAnalysisTableByTableName(@PathVariable(value = "schemaName") String schemaName) throws SQLException {
        dataBaseService.getConnection().getDataByTableNameFromAnalysis(schemaName);
        RelationSchema relation = dataBaseService.getConnection().getDatabase().getRelationSchema(schemaName);
        AnalysisDB analysisDB = new AnalysisDB();
        analysisDB.setRelationSchema(relation);
        analysisDB.analysis();
        WorkflowHistory workflowHistory = new WorkflowHistory();
        workflowHistory.setAction("Анализ данных " + schemaName);
        workflowHistory.setStatus("Выполнено");
        workflowHistory.setDateTime(new Date());
        workflowHistory.setUserId(userService.getUserByLogin(getPrincipal()));
        workFlowHistoryService.addHistory(workflowHistory);
        return relation;
    }

    @RequestMapping(value = {"/database/table/analysis/{schemaName}/atomicity"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize(value = "!isAnonymous()")
    public
    @ResponseBody
    RelationSchema getAnalysisTableAtamicily(@PathVariable(value = "schemaName") String schemaName) throws SQLException {
        dataBaseService.getConnection().getDataByTableNameFromAnalysis(schemaName);
        RelationSchema relation = dataBaseService.getConnection().getDatabase().getRelationSchema(schemaName);
        AnalysisDB analysisDB = new AnalysisDB();
        analysisDB.setRelationSchema(relation);
        analysisDB.analysis();
        return relation;
    }

    @RequestMapping(value = {"/database/allConnections/"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize(value = "!isAnonymous()")
    public
    @ResponseBody
    List<ConnectionDB> getDataBaseAll(Model model) {
        return connectionDBService
                .getAllConnectionByUserID(userService.getUserByLogin(getPrincipal()));

    }

    @RequestMapping(value = {"/database/applyAction/{tableName}"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize(value = "!isAnonymous()")
    public
    @ResponseBody
    ResponseObject applyAction(@PathVariable(value = "tableName") String tableName,
                                                             Model model, @RequestParam(value = "columns[]") String... columns) {
        Database database = dataBaseService.getConnection().getDatabase();
        List<Attribute> list = database.getRelationSchema(tableName).getAttributes();
        ResponseObject responseObject = null;
        for (String column : columns) {

                RelationSchema relation = database.getRelationSchema(tableName);
                ArrayList<Attribute> attributes = relation.getAttributes();
                for (Attribute attribute : attributes) {
                    if (attribute.getArrayIndex() == Integer.parseInt(column)) {
                        if(attribute.isAtomicity()){
                            WorkflowHistory workflowHistory = new WorkflowHistory();
                            workflowHistory.setAction("Разделение данных на атомарные значения в атрибуте :" + attribute.getName());
                            workflowHistory.setDateTime(new Date());
                            workflowHistory.setUserId(userService.getUserByLogin(getPrincipal()));
                            responseObject = dataBaseService.getConnection().addRow(tableName, attributes, attribute.getAtomicityObject().getRow());
                            if(null != responseObject.getError()){
                                workflowHistory.setStatus("Не выполнено");
                            }else {
                                workflowHistory.setStatus("Выполнено");
                            }
                            workFlowHistoryService.addHistory(workflowHistory);
                        }
                    }
                }
        }
        WorkflowHistory workflowHistory = new WorkflowHistory();
        workflowHistory.setAction("Разделение данных на атомарные значения отношение" + tableName);
        workflowHistory.setStatus("Выполнено");
        workflowHistory.setDateTime(new Date());
        workflowHistory.setUserId(userService.getUserByLogin(getPrincipal()));
        workFlowHistoryService.addHistory(workflowHistory);
        return responseObject;
    }

    @RequestMapping(value = {"/database/getColumnByTableName/{tableName}"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize(value = "!isAnonymous()")
    public
    @ResponseBody
    Map<String, List<? extends Object>> getColumnByTableName(@PathVariable(value = "tableName") String tableName,
                                                             Model model, @RequestParam(value = "columns[]") String... columns) {
        Database database = dataBaseService.getConnection().getDatabase();
        List<Attribute> list = database.getRelationSchema(tableName).getAttributes();
        Map<String, List<? extends Object>> obj = new HashMap<>();
        List<Attribute> attributes = new ArrayList<>();
        for (String s : columns) {
            for (Attribute attribute : list) {
                if (attribute.getArrayIndex() == Integer.parseInt(s)) {
                    attributes.add(attribute);
                }
            }
        }
        obj.put("attribute", attributes);
        obj.put("columntype", database.getColumnTypes());
        return obj;
    }


    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    @RequestMapping(value = "/database/createtable/{tableId}", method = RequestMethod.POST,
            produces = "application/json")
    public void createTable(@PathVariable("tableId") int tableId,
                            @RequestParam String tableName,
                            @RequestParam String[] rows, Model model) throws ParseException {

//        RepairSheet repairSheet= repairSheetService.getRepairSheetById(repairId);
//        Status statusNew = statusService.getStatusById(status);
//        repairSheet.setStatus(statusNew);
//        repairSheet.setDescription(description);
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        Date newDate = formatter.parse(date);
//        if(status==5){
//            repairSheet.setEnd_date(newDate);
//        }
//        else {
//            repairSheet.setConfirm_date(newDate);
//        }
//        repairSheetService.updateRepairSheet(repairSheet);
//
//        changeStatusOfEquipment(repairSheet.getEquipment(),statusNew, repairSheet.getType_of_maintenance());
    }
}
