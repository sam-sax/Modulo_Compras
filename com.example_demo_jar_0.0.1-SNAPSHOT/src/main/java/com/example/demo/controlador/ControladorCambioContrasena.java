package com.example.demo.controlador;

import com.example.demo.modelo.Usuario;
import com.example.demo.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControladorCambioContrasena {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/usuario/cambiar-contrasena")
    public String mostrarFormularioCambioContrasena() {
        return "cambiarContrasena";
    }

    @PostMapping("/usuario/cambiar-contrasena")
    public String cambiarContrasena(@AuthenticationPrincipal User userDetails,
                                    @RequestParam String actual,
                                    @RequestParam String nueva,
                                    @RequestParam String repetir,
                                    Model model) {

        if (!nueva.equals(repetir)) {
            model.addAttribute("error", "La nueva contraseña y su repetición no coinciden");
            return "cambiarContrasena";
        }

        Usuario u = usuarioServicio.buscarUsuarioPorNombreSP(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(actual, u.getClave())) {
            model.addAttribute("error", "Contraseña actual incorrecta");
            return "cambiarContrasena";
        }

        usuarioServicio.actualizarUsuarioSP(
            u.getIdUsuario(),
            u.getUsuario(),
            nueva,
            u.isEstado(),
            u.getRol(),
            userDetails.getUsername()
        );

        model.addAttribute("mensaje", "Contraseña actualizada correctamente");
        return "login";
    }
}
