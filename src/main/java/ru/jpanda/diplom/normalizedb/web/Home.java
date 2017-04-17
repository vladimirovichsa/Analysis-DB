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
import ru.jpanda.diplom.normalizedb.domain.*;
import ru.jpanda.diplom.normalizedb.service.ConnectionDBService;
import ru.jpanda.diplom.normalizedb.service.DataBaseService;
import ru.jpanda.diplom.normalizedb.service.TableTypeService;
import ru.jpanda.diplom.normalizedb.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey on 30.01.2017.
 */

@Controller
public class Home {
    @Autowired
    private UserService userService;

    @Autowired
    private ConnectionDBService connectionDBService;

    @Autowired
    private TableTypeService tableTypeService;

    @Autowired
    private DataBaseService dataBaseService;

    @RequestMapping(value = "/", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getStarted(Model model) {
        model.addAttribute("pageTitle", "Главная");
        User userByLogin = userService.getUserByLogin(getPrincipal());
        return "homePage";
    }

    @RequestMapping(value = {"/lk"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("!isAnonymous()")
    public String getPersonalAccount(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return "personalAccount";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getAuntefication(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return "loginPage";
    }

    @RequestMapping(value = "/users/all", method = RequestMethod.GET,
            produces = "application/json")
    @PreAuthorize("!isAnonymous()")
    public
    @ResponseBody
    List<User> getUsers(Model model) {
        return userService.getUsers();
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET,
            produces = "application/json")
    @PreAuthorize("!isAnonymous()")
    public String getUsersView(Model model) {
        model.addAttribute("userForm", new User());
        model.addAttribute("usersAll", userService.getUsers());
        model.addAttribute("current_user", userService.getUserByLogin(getPrincipal()).getLast_name() + " " +
                userService.getUserByLogin(getPrincipal()).getFirst_name());
        return "usersPage";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST,
            produces = "application/json")
    @PreAuthorize(value = "!isAnonymous()")
    public String addUsers(@Valid User user, BindingResult bindingResult) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        if (bindingResult.hasErrors()) {
            return "redirect:/users";
        }
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(user.getPassword().getBytes("UTF-8"));
        byte[] digest = md.digest();
        user.setPassword(String.format("%064x", new java.math.BigInteger(1, digest)));
        userService.addUser(user);
        return "redirect:/users";
    }

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

    @RequestMapping(value = {"/database/userconnection"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize(value = "!isAnonymous()")
    public
    @ResponseBody
    List<ConnectionDB> getUserConnection() {
        return connectionDBService
                .getAllConnectionByUserID(userService.getUserByLogin(getPrincipal()));

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

    @RequestMapping(value = {"/database/allConnections/"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize(value = "!isAnonymous()")
    public
    @ResponseBody
    List<ConnectionDB> getDataBaseAll(Model model) {
        return connectionDBService
                .getAllConnectionByUserID(userService.getUserByLogin(getPrincipal()));

    }

    @RequestMapping(value = {"/userdetais"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize(value = "!isAnonymous()")
    public void getUserInfo(Model model, HttpServletRequest request) {
        User user = userService.getUserByLogin(getPrincipal());
        model.addAttribute("userDetails", user);
        request.getRequestDispatcher("userdetais.jsp");
//        return "userDetails";
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

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

//    @RequestMapping(value="/logout", method = RequestMethod.GET)
//    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null){
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        return "redirect:/login?logout";
//    }
}
