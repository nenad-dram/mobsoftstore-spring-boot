package com.endyary.mobsoftstore.user;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.endyary.mobsoftstore.config.View;

/**
 * Authentication controller class
 */
@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        if (isCurrentAuthenticationAnonymous()) {
            return View.LOGIN.toString();
        } else {
            return "redirect:/";
        }
    }

    /**
     * Returns true if the user is already authenticated, else false
     */
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return authentication instanceof AnonymousAuthenticationToken;
    }
}
