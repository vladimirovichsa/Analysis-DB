package ru.jpanda.diplom.normalizedb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.jpanda.diplom.normalizedb.core.dbconnection.data.Attribute;
import ru.jpanda.diplom.normalizedb.core.dbconnection.data.Database;
import ru.jpanda.diplom.normalizedb.core.dbconnection.data.RelationSchema;
import ru.jpanda.diplom.normalizedb.core.dbconnection.dbConnection.DbConnection;
import ru.jpanda.diplom.normalizedb.domain.ConnectionDB;
import ru.jpanda.diplom.normalizedb.domain.TableType;
import ru.jpanda.diplom.normalizedb.domain.User;
import ru.jpanda.diplom.normalizedb.service.ConnectionDBService;
import ru.jpanda.diplom.normalizedb.service.DataBaseService;
import ru.jpanda.diplom.normalizedb.service.TableTypeService;
import ru.jpanda.diplom.normalizedb.service.UserService;
import ru.jpanda.diplom.normalizedb.service.analysis.AnalysisDB;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    @RequestMapping(value = "/connections", method = RequestMethod.GET,
            produces = "application/json")
    @PreAuthorize(value = "!isAnonymous()")
    public String getConnectionsView(Model model) {
        User user = userService.getUserByLogin(getPrincipal());
        model.addAttribute("connectionForm", new ConnectionDB());
        model.addAttribute("connectionAll", connectionDBService.getAllConnectionByUserID(user));
        model.addAttribute("tableType", tableTypeService.getAllTabletype());
        return "connectionPage";
    }

    @RequestMapping(value = "/connections", method = RequestMethod.POST,
            produces = "application/json")
    @PreAuthorize(value = "!isAnonymous()")
    public String addConnections(
            @RequestParam int tableTypeId,
            @Valid ConnectionDB connectionDB, BindingResult bindingResult) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        TableType byTableTypeId = tableTypeService.findByTableTypeId(tableTypeId);
        User user = userService.getUserByLogin(getPrincipal());
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

    @RequestMapping(value = {"/database/getColumnByTableName/{tableName}"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize(value = "!isAnonymous()")
    public
    @ResponseBody
    Map<String,List<?extends Object>> getColumnByTableName(@PathVariable(value = "tableName") String tableName,
                                                    Model model, @RequestParam(value = "columns[]") String... columns) {
        Database database = dataBaseService.getConnection().getDatabase();
        List<Attribute> list = database.getRelationSchema(tableName).getAttributes();
        Map<String,List<?extends Object>> obj = new HashMap<>();
        List<Attribute> attributes = new ArrayList<>();
        for (String s : columns) {
            for (Attribute attribute : list) {
                if(attribute.getArrayIndex() == Integer.parseInt(s)){
                    attributes.add(attribute);
                }
            }
        }
        obj.put("attribute",attributes);
        obj.put("columntype",database.getColumnTypes());
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
