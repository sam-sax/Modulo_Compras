package com.example.demo.controlador;

import com.example.demo.dto.PersonaDTO;
import com.example.demo.modelo.Pais;
import com.example.demo.modelo.Persona;
import com.example.demo.modelo.Usuario;
import com.example.demo.repositorio.PaisRepository;
import com.example.demo.servicios.CiudadService;
import com.example.demo.servicios.PersonaServicio;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/personas")
public class ControladorPersona {

    @Autowired private PersonaServicio personaServicio;
    @Autowired private CiudadService ciudadService;
    @Autowired private ModelMapper modelMapper;
    @Autowired private PaisRepository paisRepo;

    @ModelAttribute("personaDTO")
    public PersonaDTO inicializarPersonaDTO() { return new PersonaDTO(); }

    private String linkSegunRol(User user) {
        if (user == null) return "/";
        if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) return "/admin/principal";
        if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_VENDEDOR"))) return "/vendedor/principal";
        return "/usuario/principal";
    }

    @GetMapping("/listado")
public String listar(@RequestParam(required = false) String q,
                     @RequestParam(required = false) String rol,
                     @RequestParam(required = false) Long ciudadId,
                     @RequestParam(required = false) String estadoCivil,
                     @AuthenticationPrincipal User user, Model model) {
    
    // --- LIMPIEZA DE PARÁMETROS PARA EL REPOSITORIO ---
    // Si q es vacío "" o solo espacios, lo volvemos null
    String queryLimpia = (q != null && !q.trim().isEmpty()) ? q.trim() : null;
    
    // Si rol es vacío, lo volvemos null
    String rolLimpio = (rol != null && !rol.trim().isEmpty()) ? rol : null;
    
    // Si ciudadId es 0 o null, lo tratamos como null para el Query
    Long idCiudadLimpio = (ciudadId != null && ciudadId > 0) ? ciudadId : null;
    
    String estadoCivilLimpio = (estadoCivil != null && !estadoCivil.trim().isEmpty()) ? estadoCivil : null;

    // 1. Buscamos con los datos ya "limpios"
    List<Persona> lista = personaServicio.listarConFiltros(queryLimpia, rolLimpio, idCiudadLimpio, estadoCivilLimpio);
    model.addAttribute("personas", lista);

    // 2. Cargamos ciudades de Paraguay (ID 171)
    Pais paraguay = paisRepo.findById(171L).orElse(null);
    if (paraguay != null) {
        model.addAttribute("ciudades", ciudadService.listarCiudadesPorPais(paraguay));
    } else {
        model.addAttribute("ciudades", ciudadService.listarCiudades());
    }

    // 3. Devolvemos los valores originales a la vista para que el usuario vea qué filtró
    model.addAttribute("q", q);
    model.addAttribute("rol", rol);
    model.addAttribute("ciudadId", ciudadId);
    model.addAttribute("estadoCivil", estadoCivil);
    model.addAttribute("linkPrincipal", linkSegunRol(user));

    return "personas-listado";
}
    @GetMapping("/form")
    public String mostrarInicio(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("linkPrincipal", linkSegunRol(user));
        return "personas-form"; // Cambiado a nuevo nombre
    }

  @PostMapping("/guardar")
public String guardarPersona(@Valid @ModelAttribute("personaDTO") PersonaDTO dto, 
                             BindingResult result,
                             @AuthenticationPrincipal User user, Model model,
                             RedirectAttributes flash) {
    
    if (result.hasErrors()) {
        model.addAttribute("linkPrincipal", linkSegunRol(user));
        return "personas-form"; 
    }

    try {
        // Configuramos ModelMapper para que sea exacto y no se confunda de columnas
        modelMapper.getConfiguration()
                  .setMatchingStrategy(org.modelmapper.convention.MatchingStrategies.STRICT)
                  .setAmbiguityIgnored(true);

        Persona persona;
        if (dto.getIdPersona() != null) {
            persona = personaServicio.buscarPorId(dto.getIdPersona());
            if (persona == null) return "redirect:/personas/listado";
            
            // Respaldo de datos que no queremos que el "map" borre
            Usuario creador = persona.getUsuario();
            Usuario vinculado = persona.getUsuarioVinculado();
            java.time.LocalDateTime fechaOriginal = persona.getFechaIngreso();

            // Pasamos los datos del DTO a la Persona
            modelMapper.map(dto, persona);
            
            // Restauramos lo que respaldamos para seguridad
            persona.setUsuario(creador);
            persona.setUsuarioVinculado(vinculado);
            persona.setFechaIngreso(fechaOriginal);
        } else {
            persona = modelMapper.map(dto, Persona.class);
        }

        // Asignamos la ciudad manualmente por su ID
        if (dto.getCiudadId() != null) {
            persona.setCiudad(ciudadService.obtenerPorId(dto.getCiudadId()));
        }

        // ?Asignar usuario logueado antes de guardar para que el trigger lo use
        String usuarioActual = (user != null) ? user.getUsername() : "ANONIMO";
        persona.setUsuarioModificacion(usuarioActual);

        // Guardar persona
        personaServicio.guardar(persona); 

        flash.addFlashAttribute("success", "¡Guardado con éxito!");
        return "redirect:/personas/listado";

    } catch (Exception e) {
        e.printStackTrace(); // Ver el error real en consola
        flash.addFlashAttribute("error", "Error: " + e.getMessage());
        return "redirect:/personas/listado";
    }
}


    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
        Persona p = personaServicio.buscarPorId(id);
        if (p == null) return "redirect:/personas/listado";
        
        PersonaDTO dto = modelMapper.map(p, PersonaDTO.class);
        if (p.getCiudad() != null) dto.setCiudadId(p.getCiudad().getId());
        
        model.addAttribute("personaDTO", dto);
        model.addAttribute("linkPrincipal", linkSegunRol(user));
        return "personas-form"; // Cambiado a nuevo nombre
    }
        @PostMapping("/eliminar/{id}")
public String eliminar(@PathVariable Long id, @AuthenticationPrincipal User user, RedirectAttributes flash) {
    try {
        String usuarioActual = (user != null) ? user.getUsername() : "SISTEMA";

        // Eliminar físicamente
        personaServicio.eliminar(id, usuarioActual);

        flash.addFlashAttribute("success", "Persona eliminada correctamente.");
    } catch (Exception e) {
        flash.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
    }
    return "redirect:/personas/listado";
}

}
