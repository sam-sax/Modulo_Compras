package com.example.demo.controlador;

import com.example.demo.dto.PersonaDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador para la vista principal genérica según el rol.
 */
@Controller
public class ControladorPrincipal {

    @GetMapping("/principal")
    public String principal(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("usuarioActual", username);
        
        
        
        // Agregamos PersonaDTO para el formulario de "Agregar Persona"
        model.addAttribute("personaDTO", new PersonaDTO());

        // Redirige según el rol
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "/admin/principal"; // coincide con tu carpeta templates/admin
        } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_VENDEDOR"))) {
            return "/vendedor/principal"; // coincide con templates/vendedor
        } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            return "/usuario/principal"; // coincide con templates/usuario
        } else {
            return "/login"; // fallback por seguridad
        }
    }
}
