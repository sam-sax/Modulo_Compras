package com.example.demo.controlador;

import com.example.demo.modelo.Persona;
import com.example.demo.modelo.Usuario;
import com.example.demo.servicios.PersonaServicio;
import com.example.demo.servicios.UsuarioServicio;
import com.example.demo.validaciones.PersonaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/personas")
public class ControladorPersona {

    @Autowired
    private PersonaServicio personaServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private PersonaValidator personaValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder){
        binder.addValidators(personaValidator);
    }

    // Helper: devuelve la URL principal según rol
    private String linkSegunRol(User userDetails) {
        if(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "/admin/principal";
        } else if(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_VENDEDOR"))) {
            return "/vendedor/principal";
        } else {
            return "/usuario/principal"; // para rol USER
        }
    }

    @GetMapping("/form")
    public String mostrarFormulario(Model model, @AuthenticationPrincipal User userDetails) {
        model.addAttribute("persona", new Persona());
        model.addAttribute("linkPrincipal", linkSegunRol(userDetails));
        return "inicio";
    }

    @PostMapping("/guardar")
    public String guardarPersona(@ModelAttribute("persona") Persona persona,
                                 BindingResult result,
                                 @AuthenticationPrincipal User userDetails,
                                 Model model) {

        // Validaciones de duplicados
        if (persona.getEmail() != null && !persona.getEmail().trim().isEmpty()) {
            Persona personaConEmail = personaServicio.buscarPorEmail(persona.getEmail());
            if (personaConEmail != null && !personaConEmail.getIdPersona().equals(persona.getIdPersona())) {
                result.rejectValue("email", "email.duplicado", "Este email ya está registrado");
            }
        }
        if (persona.getTelefono() != null && !persona.getTelefono().trim().isEmpty()) {
            Persona personaConTelefono = personaServicio.buscarPorTelefono(persona.getTelefono());
            if (personaConTelefono != null && !personaConTelefono.getIdPersona().equals(persona.getIdPersona())) {
                result.rejectValue("telefono", "telefono.duplicado", "Este teléfono ya está registrado");
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("linkPrincipal", linkSegunRol(userDetails));
            return "inicio";
        }

        Usuario usuarioActual = usuarioServicio.buscarUsuarioPorNombreSP(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        persona.setUsuario(usuarioActual);

        if(persona.getIdPersona() == null){
            Long idGenerado = personaServicio.insertarPersonaSP(
                    persona.getNombres(),
                    persona.getApellidos(),
                    persona.getTelefono(),
                    persona.getDireccion(),
                    persona.getFechaNacimiento(),
                    usuarioActual.getIdUsuario(),
                    persona.getUsuarioVinculado() != null ? persona.getUsuarioVinculado().getIdUsuario() : null,
                    persona.getEmail(),
                    persona.getEstadoCivil(),
                    persona.getCiudad(),
                    persona.getPais(),
                    persona.getNumeroCedula(),
                    persona.getRuc()
            );
            persona.setIdPersona(idGenerado);
        } else {
            personaServicio.actualizarPersonaSP(
                    persona.getIdPersona(),
                    persona.getNombres(),
                    persona.getApellidos(),
                    persona.getTelefono(),
                    persona.getDireccion(),
                    persona.getFechaNacimiento(),
                    usuarioActual.getIdUsuario(),
                    persona.getUsuarioVinculado() != null ? persona.getUsuarioVinculado().getIdUsuario() : null,
                    persona.getEmail(),
                    persona.getEstadoCivil(),
                    persona.getCiudad(),
                    persona.getPais(),
                    persona.getNumeroCedula(),
                    persona.getRuc()
            );
        }

        return "redirect:" + linkSegunRol(userDetails);
    }

    @GetMapping("/listado")
    public String listarPersonas(Model model, @AuthenticationPrincipal User userDetails) {
        List<Persona> lista = personaServicio.listarPersonaSP();
        model.addAttribute("personas", lista);
        model.addAttribute("linkPrincipal", linkSegunRol(userDetails));
        return "listado";
    }

    @GetMapping("/editar/{id}")
    public String editarPersona(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal User userDetails) {
        Persona persona = personaServicio.buscarPersonaSP(id);
        if(persona == null) return "redirect:" + linkSegunRol(userDetails);
        model.addAttribute("persona", persona);
        model.addAttribute("linkPrincipal", linkSegunRol(userDetails));
        return "inicio";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPersona(@PathVariable("id") Long id, @AuthenticationPrincipal User userDetails) {
        personaServicio.eliminarPersonaSP(id, userDetails.getUsername());
        return "redirect:" + linkSegunRol(userDetails);
    }
}
