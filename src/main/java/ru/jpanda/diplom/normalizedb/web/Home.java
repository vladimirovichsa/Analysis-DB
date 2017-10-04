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
import ru.jpanda.diplom.normalizedb.domain.*;
import ru.jpanda.diplom.normalizedb.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

/**
 * Created by Alexey on 30.01.2017.
 */

@Controller
public class Home {
    @Autowired
    private UserService userService;

    @Autowired
    private WorkFlowHistoryService workFlowHistoryService;

    @Autowired
    private UserTypeService userTypeService;

    @Autowired
    private ConnectionDBService connectionDBService;

    @Autowired
    private TableTypeService tableTypeService;

    @Autowired
    private DataBaseService dataBaseService;

    @RequestMapping(value = {"/lk"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("!isAnonymous()")
    public String getPersonalAccount(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userService.getUserByLogin(getPrincipal()));
        model.addAttribute("userType", userTypeService.getUserType());
        return "personalAccount";
    }

    @RequestMapping(value = {"/lk"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("!isAnonymous()")
    public String getUpdatePersonalInfo(@RequestParam int userTypeId, @Valid Users user, BindingResult bindingResult) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        user.setId(userService.getUserByLogin(getPrincipal()).getId());
        user.setType_id(userTypeService.getUserType(userTypeId));
        if (bindingResult.hasErrors()) {
            return "redirect:/404";
        }
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(user.getPassword().getBytes("UTF-8"));
        byte[] digest = md.digest();
        user.setPassword(String.format("%064x", new java.math.BigInteger(1, digest)));
        userService.addUser(user);
        WorkflowHistory workflowHistory = new WorkflowHistory();
        workflowHistory.setAction("Редактирование персональных данных");
        workflowHistory.setStatus("Выполнено");
        workflowHistory.setDateTime(new Date());
        workflowHistory.setUserId(userService.getUserByLogin(getPrincipal()));
        workFlowHistoryService.addHistory(workflowHistory);
        return "redirect:/lk";
    }

    @RequestMapping(value = {"", "/", "/login"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getAuntefication(Model model) {
        return "loginPage";
    }

    @RequestMapping(value = "/users/all", method = RequestMethod.GET,
            produces = "application/json")
    @PreAuthorize("!isAnonymous()")
    public
    @ResponseBody
    List<Users> getUsers(Model model) {
        return userService.getUsers();
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET,
            produces = "application/json")
    @PreAuthorize("!isAnonymous()")
    public String getUsersView(Model model) {
        model.addAttribute("userForm", new Users());
        model.addAttribute("usersAll", userService.getUsers());
        model.addAttribute("current_user", userService.getUserByLogin(getPrincipal()).getLast_name() + " " +
                userService.getUserByLogin(getPrincipal()).getFirst_name());
        return "usersPage";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST,
            produces = "application/json")
    @PreAuthorize(value = "!isAnonymous()")
    public String addUsers(@Valid Users user, BindingResult bindingResult) throws NoSuchAlgorithmException, UnsupportedEncodingException {

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

    @RequestMapping(value = "/users/allusers", method = RequestMethod.GET,
            produces = "application/json")
    @PreAuthorize(value = "!isAnonymous()")
    public @ResponseBody
    List<Users> getHistory(Model model){
        return userService.getUsers();
    }

    @RequestMapping(value = "/users/history", method = RequestMethod.POST,
            produces = "application/json")
    @PreAuthorize(value = "!isAnonymous()")
    public String getHistoryUser(@RequestParam int userid){
        return "redirect:/users/" + userid + "/history";
    }

    @RequestMapping(value = "/users/{userId}/history", method = RequestMethod.GET,
            produces = "application/json")
    @PreAuthorize(value = "!isAnonymous()")
    public String getHistoryUserById(@PathVariable(value = "userId") int userId, Model model){
        model.addAttribute("listHistory",workFlowHistoryService.getHistoryByUser(userService.getUserById(userId)));
        WorkflowHistory workflowHistory = new WorkflowHistory();
        workflowHistory.setAction("Открытие историй пользователя" + userService.getUserById(userId).getLogin() );
        workflowHistory.setStatus("Выполнено");
        workflowHistory.setDateTime(new Date());
        workflowHistory.setUserId(userService.getUserByLogin(getPrincipal()));
        workFlowHistoryService.addHistory(workflowHistory);
        return "historyPage";
    }

    @RequestMapping(value = {"/userdetais"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize(value = "!isAnonymous()")
    public void getUserInfo(Model model, HttpServletRequest request) {
        Users user = userService.getUserByLogin(getPrincipal());
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
        WorkflowHistory workflowHistory = new WorkflowHistory();
        workflowHistory.setAction("Выход из системы САНРБД" );
        workflowHistory.setStatus("Выполнено");
        workflowHistory.setDateTime(new Date());
        workflowHistory.setUserId(userService.getUserByLogin(getPrincipal()));
        workFlowHistoryService.addHistory(workflowHistory);
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
