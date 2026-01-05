package com.example.demo.controlador;

import com.example.demo.modelo.Usuario;
import com.example.demo.modelo.Persona;
import com.example.demo.modelo.Vendedor;
import com.example.demo.servicios.UsuarioServicio;
import com.example.demo.servicios.PersonaServicio;
import com.example.demo.servicios.VendedorService;
import com.example.demo.validaciones.UsuarioValidator;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class ControladorAdmin {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private VendedorService vendedorService;

    @Autowired
    private PersonaServicio personaServicio;

    @Autowired
    private UsuarioValidator usuarioValidator;

    // =========================
    // PANEL PRINCIPAL ADMIN
    // =========================
    @GetMapping("/principal")
public String principalAdmin(Model model, Principal principal) {
    String username = principal.getName();
    model.addAttribute("usuarioActual", username);

    Usuario usuario = usuarioServicio.buscarUsuarioPorNombreSP(username).orElse(null);
    if (usuario != null && usuario.getPersona() != null) {
        model.addAttribute("nombreCompleto",
                usuario.getPersona().getNombres() + " " + usuario.getPersona().getApellidos());
    } else {
        model.addAttribute("nombreCompleto", username); // fallback
    }

    model.addAttribute("usuarios", usuarioServicio.listarUsuarioSP());
    return "admin/principal";
}



    // =========================
    // CREAR VENDEDOR (ADMIN SOLO)
    // =========================
    @GetMapping("/vendedor/nuevo")
    public String mostrarFormularioVendedor(Model model) {
        model.addAttribute("usuario", new Usuario());
        List<Persona> personasSinUsuario = personaServicio.buscarPersonasSinUsuario();
        model.addAttribute("personas", personasSinUsuario);
        return "vendedor/vendedor_form";
    }

    @PostMapping("/vendedor/guardar")
    public String guardarVendedor(@RequestParam("personaId") Long personaId,
                                  @RequestParam("usuario") String username,
                                  @RequestParam("clave") String clave,
                                  @RequestParam(value = "codigoVendedor", required = false) String codigoVendedor,
                                  RedirectAttributes redirectAttributes) {

        if (usuarioServicio.existeNombre(username)) {
            redirectAttributes.addFlashAttribute("error",
                    "❌ El usuario '" + username + "' ya existe");
            return "redirect:/admin/vendedor/nuevo";
        }

        Persona persona = personaServicio.buscarPorId(personaId);
        if (persona == null || persona.getUsuarioVinculado() != null) {
            redirectAttributes.addFlashAttribute("error",
                    "❌ Persona inválida o ya tiene usuario asignado");
            return "redirect:/admin/vendedor/nuevo";
        }

        Usuario nuevo = new Usuario();
        nuevo.setUsuario(username);
        nuevo.setClave(clave);
        nuevo.setRol("VENDEDOR");
        nuevo.setEstado(true);
        nuevo.setPersona(persona);

        Long idGenerado = usuarioServicio.insertarUsuarioSP(
                nuevo.getUsuario(),
                nuevo.getClave(),
                nuevo.isEstado(),
                nuevo.getRol(),
                persona
        );
        nuevo.setId(idGenerado);

        Vendedor vendedor = new Vendedor();
        vendedor.setPersona(persona);
        vendedor.setUsuario(nuevo);

        if (codigoVendedor == null || codigoVendedor.isEmpty()) {
            codigoVendedor = "VEND-" + System.currentTimeMillis();
        }
        vendedor.setCodigoVendedor(codigoVendedor);

        vendedorService.registrarDesdeEntidad(vendedor);

        redirectAttributes.addFlashAttribute("mensaje",
                "✅ Vendedor '" + username + "' registrado correctamente con código: " + codigoVendedor);
        return "redirect:/admin/vendedor/listado";
    }

    // =========================
    // LISTADO DE VENDEDORES (ADMIN)
    // =========================
    @GetMapping("/vendedor/listado")
    public String listarVendedores(Model model) {
        List<Vendedor> vendedores = vendedorService.listarActivos();
        model.addAttribute("vendedores", vendedores);
        return "vendedor/vendedor_lista";
    }

    // =========================
    // CAMBIAR ROL / ESTADO (ADMIN)
    // =========================
    @PostMapping("/usuarios/cambiar-rol")
    public String cambiarRolUsuario(@RequestParam Long id,
                                    @RequestParam String rol,
                                    @RequestParam Boolean estado) {
        usuarioServicio.cambiarRolUsuarioSP(id, rol, estado, "admin");
        return "redirect:/admin/principal";
    }

    // =========================
    // DESACTIVAR USUARIO
    // =========================
    @GetMapping("/usuarios/desactivar/{id}")
    public String desactivarUsuario(@PathVariable Long id) {
        usuarioServicio.cambiarEstado(id, false); // false = inactivo
        return "redirect:/admin/principal";
    }

    // =========================
    // ACTIVAR USUARIO
    // =========================
    @GetMapping("/usuarios/activar/{id}")
    public String activarUsuario(@PathVariable Long id) {
        usuarioServicio.cambiarEstado(id, true); // true = activo
        return "redirect:/admin/principal";
    }

}
