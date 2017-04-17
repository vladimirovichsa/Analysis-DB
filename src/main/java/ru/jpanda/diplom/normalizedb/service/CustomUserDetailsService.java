package ru.jpanda.diplom.normalizedb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.jpanda.diplom.normalizedb.domain.User;
import ru.jpanda.diplom.normalizedb.domain.UserType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexey Storozhev
 */
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        User user = userService.getUserByLogin(login);
        System.out.println("Users" + userService.getUsers().size());
        if (userService.getUsers().get(0) != null) {
            System.out.println("User #1" + userService.getUsers().get(0).getLogin());
        }
        System.out.println("User : " + user + "login: " + login);
        if (user == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("Username not found");
        }

        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(),
                true, true, true, true, getGrantedAuthorities(user));
    }


    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        System.out.println("UserProfile : " + user.getFirst_name());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getType().getType()));
        System.out.print("authorities :" + authorities);
        return authorities;
    }
}
