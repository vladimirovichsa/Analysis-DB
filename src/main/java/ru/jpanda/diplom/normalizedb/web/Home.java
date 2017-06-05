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
import ru.jpanda.diplom.normalizedb.core.dbconnection.data.Database;
import ru.jpanda.diplom.normalizedb.core.dbconnection.data.RelationSchema;
import ru.jpanda.diplom.normalizedb.core.dbconnection.dbConnection.DbConnection;
import ru.jpanda.diplom.normalizedb.service.analysis.*;
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

    @RequestMapping(value = "/home", method = RequestMethod.GET,
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

    @RequestMapping(value = {"","/", "/login"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getAuntefication(Model model) {
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
