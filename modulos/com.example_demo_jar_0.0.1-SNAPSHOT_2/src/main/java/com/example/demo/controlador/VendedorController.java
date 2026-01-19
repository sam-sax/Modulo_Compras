package com.example.demo.controlador;

import com.example.demo.servicios.PersonaServicio;
import com.example.demo.servicios.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;

@Controller
@RequestMapping("/vendedor")
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;

    @Autowired
    private PersonaServicio personaServicio;

    @GetMapping("/principal")
    public String panelVendedor(Model model, Principal principal) {
        String username = principal.getName();
        var vendedor = vendedorService.buscarPorUsuario(username);

        String nombreCompleto = username;
        String rol = "VENDEDOR";

        if (vendedor != null) {
            if (vendedor.getUsuario() != null) {
                rol = vendedor.getUsuario().getRol();
            }
            if (vendedor.getPersona() != null) {
                nombreCompleto = vendedor.getPersona().getNombres() + " " + vendedor.getPersona().getApellidos();
            }
        }

        model.addAttribute("usuarioActual", nombreCompleto);
        model.addAttribute("rolUsuario", rol);
        model.addAttribute("cantidadClientes", personaServicio.listar().size());

        // Retorno ajustado para carpetas planas de NetBeans
        return "vendedor/principal";
    }
}