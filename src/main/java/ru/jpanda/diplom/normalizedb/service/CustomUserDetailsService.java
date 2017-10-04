package ru.jpanda.diplom.normalizedb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.jpanda.diplom.normalizedb.domain.Users;
import ru.jpanda.diplom.normalizedb.domain.WorkflowHistory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Alexey Storozhev
 */
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private WorkFlowHistoryService workFlowHistoryService;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        Users user = userService.getUserByLogin(login);
        System.out.println("Users" + userService.getUsers().size());
        if (userService.getUsers().get(0) != null) {
            System.out.println("Users #1" + userService.getUsers().get(0).getLogin());
        }
        System.out.println("Users : " + user + "login: " + login);
        WorkflowHistory workflowHistory = new WorkflowHistory();
        workflowHistory.setAction("Вход в систему САНРБД" );

        if (user == null) {
            System.out.println("Users not found");
            workflowHistory.setStatus("Не выполнено");
            throw new UsernameNotFoundException("Username not found");
        }else {
            workflowHistory.setStatus("Выполнено");
        }
        workflowHistory.setDateTime(new Date());
        workflowHistory.setUserId(user);
        workFlowHistoryService.addHistory(workflowHistory);
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(),
                true, true, true, true, getGrantedAuthorities(user));
    }


    private List<GrantedAuthority> getGrantedAuthorities(Users user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        System.out.println("UserProfile : " + user.getFirst_name());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getType_id().getType()));
        System.out.print("authorities :" + authorities);
        return authorities;
    }
}
