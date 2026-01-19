package com.example.demo.controlador;

import com.example.demo.dto.PersonaDTO;
import com.example.demo.servicios.PersonaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControladorPrincipal {

    @Autowired
    private PersonaServicio personaServicio;

    @GetMapping("/principal")
    public String principal(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        String rol = auth.getAuthorities().toString();

        model.addAttribute("usuarioActual", username);
        model.addAttribute("rolUsuario", rol);
        model.addAttribute("personaDTO", new PersonaDTO());
        model.addAttribute("personas", personaServicio.listar()); 

        // --- LÓGICA DE REDIRECCIÓN POR ROL ---
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "admin/principal";
            
        } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_VENDEDOR"))) {
            return "vendedor/principal";
            
        } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_COMPRADOR"))) {
            // --- AÑADIDO: Redirección para el nuevo rol COMPRADOR ---
            return "comprador/principal"; 
            
        } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            return "usuario/principal";
            
        } else {
            return "login";
        }
    }
}