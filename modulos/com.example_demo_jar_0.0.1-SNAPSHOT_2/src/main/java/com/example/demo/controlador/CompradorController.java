package com.example.demo.controlador;

import com.example.demo.servicios.CompradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;

@Controller
@RequestMapping("/comprador")
public class CompradorController {

    @Autowired
    private CompradorService compradorService;

    @GetMapping("/principal")
    public String panelComprador(Model model, Principal principal) {
        String username = principal.getName();
        var comprador = compradorService.buscarPorUsuario(username);

        String nombreCompleto = username;
        String rol = "COMPRADOR";

        if (comprador != null) {
            if (comprador.getUsuario() != null) {
                rol = comprador.getUsuario().getRol();
            }
            if (comprador.getPersona() != null) {
                nombreCompleto = comprador.getPersona().getNombres() + " " + comprador.getPersona().getApellidos();
            }
        }

        model.addAttribute("usuarioActual", nombreCompleto);
        model.addAttribute("rolUsuario", rol);
        
        // Aquí podrías agregar contadores de compras pendientes, etc.
        model.addAttribute("tareasPendientes", 0); 

        return "comprador/principal";
    }
}