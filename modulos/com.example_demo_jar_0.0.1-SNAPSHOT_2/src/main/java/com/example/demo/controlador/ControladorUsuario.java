package com.example.demo.controlador;

import com.example.demo.dto.UsuarioRegistroDTO;
import com.example.demo.modelo.Persona;
import com.example.demo.servicios.UsuarioServicio;
import com.example.demo.servicios.PersonaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/registro")
public class ControladorUsuario {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private PersonaServicio personaServicio;

    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioRegistroDTO());
        // Filtra personas que no tienen usuario vinculado ni cuenta principal
        List<Persona> personasSinUsuario = personaServicio.buscarPersonasSinUsuario();
        model.addAttribute("personas", personasSinUsuario);
        return "registroUsuario"; 
    }

    @PostMapping
    public String guardarUsuario(@ModelAttribute("usuarioDTO") UsuarioRegistroDTO dto,
                                 RedirectAttributes redirectAttributes) {

        if (usuarioServicio.existeNombre(dto.getUsuario())) {
            redirectAttributes.addFlashAttribute("error", "❌ El usuario '" + dto.getUsuario() + "' ya existe");
            return "redirect:/admin/registro";
        }

        Persona persona = personaServicio.buscarPorId(dto.getPersonaId());
        
        usuarioServicio.insertarUsuarioSP(
            dto.getUsuario(),
            dto.getClave(),
            true, 
            dto.getRol(),
            persona
        );

        redirectAttributes.addFlashAttribute("mensaje", "✅ Usuario creado exitosamente");
        return "redirect:/admin/registro/exito";
    }

    @GetMapping("/exito")
    public String registroExito() {
        return "registroExito";
    }
}