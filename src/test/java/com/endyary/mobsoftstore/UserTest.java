package com.endyary.mobsoftstore;


import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;

import com.endyary.mobsoftstore.user.Role;
import com.endyary.mobsoftstore.user.User;
import com.endyary.mobsoftstore.user.UserService;

@SpringBootTest
@ActiveProfiles("embedded-db")
class UserTest {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    @Test
    void findByUsername_validUsername_foundWithValidRole() {
        Optional<User> dbUser = userService.findByUsername("developer1");
        User user = null;
        if (dbUser.isPresent()) {
            user = dbUser.get();
        }
        assert user != null;
        Assertions.assertEquals(Role.DEVELOPER, user.getRole());
    }

    @Test
    void findByUsername_invalidUsername_notFound() {
        Optional<User> dbUser = userService.findByUsername("developer123");
        Assertions.assertFalse(dbUser.isPresent());
    }

    @Test
    void authUser_validCredentials_authenticated() throws Exception {
        String username = "developer1";
        String password = "password";
        GrantedAuthority roleDeveloper = new SimpleGrantedAuthority(Role.DEVELOPER.name());

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, password);

        Authentication auth = getAuthenticationManager().authenticate(authToken);
        Assertions.assertTrue(auth.getAuthorities().contains(roleDeveloper));
    }

    @Test
    void authUser_invalidCredentials_throwBadCredentials() throws Exception {
        String username = "developer123";
        String password = "password";

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, password);
        AuthenticationManager authenticationManager = getAuthenticationManager();

        Assertions.assertThrows(BadCredentialsException.class,
                () -> authenticationManager.authenticate(authToken));
    }

    private AuthenticationManager getAuthenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
