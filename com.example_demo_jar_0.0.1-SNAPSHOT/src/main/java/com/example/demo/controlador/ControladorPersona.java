package com.example.demo.controlador;

import com.example.demo.dto.PersonaDTO;
import com.example.demo.modelo.Pais;
import com.example.demo.modelo.Persona;
import com.example.demo.modelo.TipoPersona;
import com.example.demo.servicios.CiudadService;
import com.example.demo.servicios.PersonaServicio;
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

    // ================================
    // Inicialización del DTO global
    // ================================
    @ModelAttribute("personaDTO")
    public PersonaDTO inicializarPersonaDTO() {
        return new PersonaDTO();
    }

    private String linkSegunRol(User user) {
        if (user == null) return "/";
        if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) return "/admin/principal";
        if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_VENDEDOR"))) return "/vendedor/principal";
        return "/usuario/principal";
    }

    // ================================
    // Página de inicio
    // ================================
    @GetMapping("/inicio")
    public String mostrarInicio(Model model, @AuthenticationPrincipal User user) {
        // Aseguramos que personaDTO siempre exista
        if (!model.containsAttribute("personaDTO")) {
            model.addAttribute("personaDTO", new PersonaDTO());
        }
        model.addAttribute("linkPrincipal", linkSegunRol(user));
        return "inicio"; // Thymeleaf: templates/inicio.html
    }

    // ================================
    // Formulario de creación/edición
    // ================================
   

    // ================================
    // Guardar Persona
    // ================================
    @PostMapping("/guardar")
    public String guardarPersona(@ModelAttribute("personaDTO") PersonaDTO dto,
                                 BindingResult result,
                                 @AuthenticationPrincipal User user,
                                 Model model) {

        // Validar email duplicado
        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            Persona pExistente = personaServicio.buscarPorEmail(dto.getEmail());
            if (pExistente != null && (dto.getIdPersona() == null || !pExistente.getIdPersona().equals(dto.getIdPersona()))) {
                result.rejectValue("email", "error.email", "El email ya existe");
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("linkPrincipal", linkSegunRol(user));
            model.addAttribute("personaDTO", dto);
            return "inicio";
        }

        Persona persona;
        if (dto.getIdPersona() != null) {
            persona = personaServicio.buscarPorId(dto.getIdPersona());
            modelMapper.map(dto, persona);
            persona.setFechaNacimiento(dto.getFechaNacimiento());
        } else {
            persona = modelMapper.map(dto, Persona.class);
        }

        // Asignar ciudad
        if (dto.getCiudadId() != null) {
            persona.setCiudad(ciudadService.obtenerPorId(dto.getCiudadId()));
        } else {
            persona.setCiudad(null);
        }

        // Booleanos roles
        persona.setEsCliente(Boolean.TRUE.equals(dto.getEsCliente()));
        persona.setEsProveedor(Boolean.TRUE.equals(dto.getEsProveedor()));
        persona.setEsVendedor(Boolean.TRUE.equals(dto.getEsVendedor()));
        persona.setEsComprador(Boolean.TRUE.equals(dto.getEsComprador()));

        if (persona.getIdPersona() == null) {
            personaServicio.insertar(persona);
        } else {
            personaServicio.actualizar(persona);
        }

        return "redirect:/personas/listado";
    }

    // ================================
    // Listado de personas con filtros
    // ================================
    @GetMapping("/listado")
    public String listar(@RequestParam(required = false) String q,
                         @RequestParam(required = false) String rol,
                         @RequestParam(required = false) Long ciudadId,
                         @RequestParam(required = false) String estadoCivil,
                         Model model, @AuthenticationPrincipal User user) {

        List<Persona> lista = personaServicio.listarConFiltros(q, rol, ciudadId, estadoCivil);

        model.addAttribute("personas", lista);
        model.addAttribute("q", q);
        model.addAttribute("rol", rol);
        model.addAttribute("ciudadId", ciudadId);
        model.addAttribute("estadoCivil", estadoCivil);
        model.addAttribute("linkPrincipal", linkSegunRol(user));

        // Cargar ciudades de Paraguay
        final Long PARAGUAY_ID = 171L;
        model.addAttribute("ciudades", ciudadService.listarCiudadesPorPais(new Pais(PARAGUAY_ID)));

        return "listado";
    }

    @GetMapping("/editar/{id}")
public String editar(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
    Persona p = personaServicio.buscarPorId(id);
    if (p == null) return "redirect:/personas/listado";

    PersonaDTO dto = new PersonaDTO();

    dto.setIdPersona(p.getIdPersona());
    // Normalizar tipoPersona
    dto.setTipoPersona(p.getTipoPersona() != null ? p.getTipoPersona() : TipoPersona.FISICA);

    dto.setNombres(p.getNombres());
    dto.setApellidos(p.getApellidos());
    dto.setRazonSocial(p.getRazonSocial());
    dto.setRepresentanteLegal(p.getRepresentanteLegal());
    dto.setTelefono(p.getTelefono());
    dto.setDireccion(p.getDireccion());
    dto.setEmail(p.getEmail());
    dto.setEstadoCivil(p.getEstadoCivil());
    dto.setNumeroCedula(p.getNumeroCedula());
    dto.setRuc(p.getRuc());

    // ✅ siempre asignar fecha de nacimiento
    dto.setFechaNacimiento(p.getFechaNacimiento());

    if (p.getCiudad() != null) {
        dto.setCiudadId(p.getCiudad().getId());
        dto.setCiudadNombre(p.getCiudad().getNombre());
        if (p.getCiudad().getPais() != null) {
            dto.setPaisId(p.getCiudad().getPais().getId());
            dto.setPaisNombre(p.getCiudad().getPais().getNombre());
        }
    }

    dto.setEsCliente(Boolean.TRUE.equals(p.getEsCliente()));
    dto.setEsProveedor(Boolean.TRUE.equals(p.getEsProveedor()));
    dto.setEsVendedor(Boolean.TRUE.equals(p.getEsVendedor()));
    dto.setEsComprador(Boolean.TRUE.equals(p.getEsComprador()));

    model.addAttribute("personaDTO", dto);
    model.addAttribute("linkPrincipal", linkSegunRol(user));

    return "inicio";
}

    // ================================
    // Desactivar persona
    // ================================
    @GetMapping("/desactivar/{id}")
    public String desactivar(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        Persona persona = personaServicio.buscarPorId(id);
        if (persona == null) {
            redirectAttrs.addFlashAttribute("mensajeError", "Persona no encontrada.");
            return "redirect:/personas/listado";
        }

        persona.setEsCliente(false);
        persona.setEsProveedor(false);
        persona.setEsVendedor(false);
        persona.setEsComprador(false);
        personaServicio.actualizar(persona);

        redirectAttrs.addFlashAttribute("mensajeExito", "Persona desactivada correctamente. Eliminación física solo desde DB.");
        return "redirect:/personas/listado";
    }
}
