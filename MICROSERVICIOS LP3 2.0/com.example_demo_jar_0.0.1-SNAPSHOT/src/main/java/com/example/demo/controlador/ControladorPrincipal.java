package com.example.demo.controlador;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Vista principal genérica.
 */
@Controller
public class ControladorPrincipal {

    @GetMapping("/principal")
    public String principal(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("usuarioActual", username);

        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "adminprincipal";
        } else if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            return "usuarioprincipal";
        } else if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_VENDEDOR"))) {
            return "vendedorprincipal";
        } else {
            return "login"; // por seguridad
        }
    }
}
