package com.example.demo.controlador;

import com.example.demo.modelo.Comprador;
import com.example.demo.modelo.Usuario;
import com.example.demo.modelo.Persona;
import com.example.demo.modelo.Vendedor;
import com.example.demo.servicios.CompradorService;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

@Controller
@RequestMapping("/admin")
public class ControladorAdmin {

    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private CompradorService compradorService;

    @Autowired
    private VendedorService vendedorService;

    @Autowired
    private PersonaServicio personaServicio;

    @Autowired
    private UsuarioValidator usuarioValidator;

    @GetMapping("/principal")
    public String principalAdmin(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("usuarioActual", username);

        Usuario usuario = usuarioServicio.buscarUsuarioPorNombreSP(username).orElse(null);
        if (usuario != null && usuario.getPersona() != null) {
            model.addAttribute("nombreCompleto",
                    usuario.getPersona().getNombres() + " " + usuario.getPersona().getApellidos());
        } else {
            model.addAttribute("nombreCompleto", username);
        }

        model.addAttribute("usuarios", usuarioServicio.listarUsuariosDetalleSP());
        return "admin/principal";
    }

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
            redirectAttributes.addFlashAttribute("error", "❌ El usuario '" + username + "' ya existe");
            return "redirect:/admin/vendedor/nuevo";
        }

        Persona persona = personaServicio.buscarPorId(personaId);
        if (persona == null) {
            redirectAttributes.addFlashAttribute("error", "❌ Persona no encontrada");
            return "redirect:/admin/vendedor/nuevo";
        }

        Long idGenerado = usuarioServicio.insertarUsuarioSP(username, clave, true, "VENDEDOR", persona);

        Usuario nuevo = new Usuario();
        nuevo.setId(idGenerado);

        Vendedor vendedor = new Vendedor();
        vendedor.setPersona(persona);
        vendedor.setUsuario(nuevo);

        if (codigoVendedor == null || codigoVendedor.isEmpty()) {
            codigoVendedor = "VEND-" + System.currentTimeMillis();
        }
        vendedor.setCodigoVendedor(codigoVendedor);

        vendedorService.registrarDesdeEntidad(vendedor);

        redirectAttributes.addFlashAttribute("mensaje", "✅ Vendedor registrado con éxito");
        return "redirect:/admin/vendedor/listado";
    }

    @GetMapping("/vendedor/listado")
    public String listarVendedores(Model model) {
        List<Vendedor> vendedores = vendedorService.listarActivos();
        model.addAttribute("vendedores", vendedores);
        return "vendedor/vendedor_lista";
    }

@PostMapping("/usuarios/cambiar-rol")
public String cambiarRol(@RequestParam("id") Long id, @RequestParam("rol") String rol, Principal principal) {
    
    usuarioServicio.cambiarRolUsuarioSP(id, rol, true, principal.getName());

    // SI EL USUARIO QUE CAMBIAS ES EL QUE ESTÁ LOGUEADO ACTUALMENTE
    if (principal.getName().equals(usuarioServicio.buscarPorId(id).getUsuario())) {
        // Actualizamos las autoridades en la sesión "al vuelo"
        List<GrantedAuthority> actualizadas = List.of(new SimpleGrantedAuthority("ROLE_" + rol.toUpperCase()));
        Authentication nuevaAuth = new UsernamePasswordAuthenticationToken(principal.getName(), null, actualizadas);
        SecurityContextHolder.getContext().setAuthentication(nuevaAuth);
    }

    return "redirect:/admin/principal";
}
    @GetMapping("/usuarios/desactivar/{id}")
    public String desactivarUsuario(@PathVariable Long id) {
        usuarioServicio.cambiarEstado(id, false);
        return "redirect:/admin/principal";
    }

    @GetMapping("/usuarios/activar/{id}")
    public String activarUsuario(@PathVariable Long id) {
        usuarioServicio.cambiarEstado(id, true);
        return "redirect:/admin/principal";
    }
    
  @GetMapping("/comprador/nuevo")
public String mostrarFormularioComprador(Model model, Principal principal) {
    // 1. Agregamos el usuario actual para que el fragmento 'menu' no falle
    String username = principal.getName();
    model.addAttribute("usuarioActual", username);

    model.addAttribute("usuario", new Usuario());
    
    // 2. Traemos las personas que no tienen usuario
    List<Persona> personasSinUsuario = personaServicio.buscarPersonasSinUsuario();
    model.addAttribute("personas", personasSinUsuario);
    
    return "comprador/comprador_form"; 
}

@PostMapping("/comprador/guardar")
public String guardarComprador(@RequestParam("personaId") Long personaId,
                               @RequestParam("usuario") String username,
                               @RequestParam("clave") String clave,
                               @RequestParam(value = "codigoComprador", required = false) String codigoComprador,
                               RedirectAttributes redirectAttributes) {

    if (usuarioServicio.existeNombre(username)) {
        redirectAttributes.addFlashAttribute("error", "❌ El usuario '" + username + "' ya existe");
        return "redirect:/admin/comprador/nuevo";
    }

    Persona persona = personaServicio.buscarPorId(personaId);
    if (persona == null) {
        redirectAttributes.addFlashAttribute("error", "❌ Persona no encontrada");
        return "redirect:/admin/comprador/nuevo";
    }

    // 1. Insertamos el usuario con Rol COMPRADOR usando tu SP
    Long idGenerado = usuarioServicio.insertarUsuarioSP(username, clave, true, "COMPRADOR", persona);

    Usuario nuevo = new Usuario();
    nuevo.setId(idGenerado);

    // 2. Creamos la entidad Comprador vinculada
    Comprador comprador = new Comprador();
    comprador.setPersona(persona);
    comprador.setUsuario(nuevo);

    if (codigoComprador == null || codigoComprador.isEmpty()) {
        codigoComprador = "COMP-" + System.currentTimeMillis();
    }
    comprador.setCodigoComprador(codigoComprador);

    compradorService.registrar(comprador); // O el método que definiste en tu service

    redirectAttributes.addFlashAttribute("mensaje", "✅ Comprador registrado con éxito");
    return "redirect:/admin/comprador/listado";
}




@GetMapping("/comprador/listado")
@Transactional(readOnly = true) // <-- ESTO SOLUCIONA EL ERROR "NO SESSION"
public String listarCompradores(Model model, Principal principal) {
    // 1. Cargamos el usuario actual para el fragmento 'menu'
    String username = principal.getName();
    model.addAttribute("usuarioActual", username);

    // 2. Cargamos la lista de compradores
    List<Comprador> compradores = compradorService.listarActivos();
    model.addAttribute("compradores", compradores);
    
    return "comprador/comprador_lista"; 
}
}
