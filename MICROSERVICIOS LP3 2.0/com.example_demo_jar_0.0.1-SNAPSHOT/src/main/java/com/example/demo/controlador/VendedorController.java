package com.example.demo.controlador;

import com.example.demo.servicios.PersonaServicio;
import com.example.demo.servicios.VendedorService;
import com.example.demo.modelo.Usuario;
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

        // Obtenemos el username del vendedor logueado
        String username = principal.getName();

        // Buscamos al vendedor por su usuario
        var vendedor = vendedorService.buscarPorUsuario(username);

                // Nombre completo o username
                String nombreCompleto;
                String rol = "VENDEDOR"; // default

                if (vendedor != null) {
                    // Obtenemos el usuario asociado al vendedor
                    if (vendedor.getUsuario() != null && vendedor.getUsuario().getRol() != null) {
                        rol = vendedor.getUsuario().getRol();
                    }

                    if (vendedor.getPersona() != null) {
                        nombreCompleto = vendedor.getPersona().getNombres() + " " + vendedor.getPersona().getApellidos();
                    } else {
                        nombreCompleto = username;
                    }
                } else {
                    nombreCompleto = username;
                }


        model.addAttribute("usuarioActual", nombreCompleto);
        model.addAttribute("rolUsuario", rol);

        // Cantidad de clientes (o datos que quieras mostrar al vendedor)
        model.addAttribute("cantidadClientes", personaServicio.listar().size());

        return "vendedor/principal";
    }
}
