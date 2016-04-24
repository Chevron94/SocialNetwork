package network.services;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import network.dao.UserDao;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by roman on 9/29/15.
 */
@Service
public class NetworkUserDetailsService implements UserDetailsService {

    @EJB
    UserDao userService;

    public void setUserService(UserDao userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        network.entity.User user = userService.getUserByLogin(s);
        if (user != null && user.getConfirmed() == false){
            throw new UsernameNotFoundException("Inactive user");
        }
        User loadedUser =  new User(user.getLogin(),
                user.getPassword(),
                user.getConfirmed(),
                true,
                true,
                !user.getIsLocked(),
                getAuthorities(user.getRole()));
        return loadedUser;
    }

    public Collection getAuthorities(Integer role) {
        List authList = getGrantedAuthorities(getRoles(role));
        return authList;
    }

    public List getRoles(int role)
    {
        List roles = new ArrayList();
        if (role==1)
        {
            roles.add("ROLE_ADMIN");
            roles.add("ROLE_USER");
        }else roles.add("ROLE_USER");
        return roles;
    }

    public static List getGrantedAuthorities(List roles) {
        List authorities = new ArrayList();
        for (Object role : roles) {
            authorities.add(new SimpleGrantedAuthority(String.valueOf(role)));
        }
        return authorities;
    }
}
