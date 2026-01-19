package com.example.demo.controlador;

import com.example.demo.modelo.Usuario;
import com.example.demo.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import org.springframework.transaction.annotation.Transactional;

@Controller
@RequestMapping("/usuario")
public class ControladorUsuarios {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/principal")
    @Transactional
    public String principalUsuario(Model model, Principal principal) {
        String username = principal.getName();

        // Obtenemos el usuario completo desde la DB
        Usuario usuarioCompleto = usuarioServicio.buscarUsuarioPorNombreSP(username)
                                                 .orElse(null);

        // Nombre completo o username
        String nombreCompleto;
        String rol = "USER"; // rol por defecto
        if (usuarioCompleto != null) {
            rol = usuarioCompleto.getRol() != null ? usuarioCompleto.getRol() : "USER";
            if (usuarioCompleto.getPersona() != null) {
                nombreCompleto = usuarioCompleto.getPersona().getNombres() + " " + usuarioCompleto.getPersona().getApellidos();
            } else {
                nombreCompleto = username;
            }
        } else {
            nombreCompleto = username;
        }

        model.addAttribute("usuarioActual", nombreCompleto);
        model.addAttribute("rolUsuario", rol);

        return "usuario/principal";
    }
}
