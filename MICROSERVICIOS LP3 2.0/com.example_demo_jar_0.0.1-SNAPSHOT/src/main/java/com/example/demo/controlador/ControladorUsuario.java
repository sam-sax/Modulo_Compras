package com.example.demo.controlador;

import com.example.demo.modelo.Usuario;
import com.example.demo.modelo.Persona;
import com.example.demo.servicios.UsuarioServicio;
import com.example.demo.servicios.PersonaServicio;
import com.example.demo.validaciones.UsuarioValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/registro")
public class ControladorUsuario {
    
    
    // para crear usuarios

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private PersonaServicio personaServicio;

    @Autowired
    private UsuarioValidator usuarioValidator;

    // Mostrar formulario - FILTRAR personas sin usuario
    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        
        // SOLO personas que NO tienen usuario asociado
        List<Persona> personasSinUsuario = personaServicio.buscarPersonasSinUsuario();
        model.addAttribute("personas", personasSinUsuario);
        
        return "registroUsuario";
    }

    // Guardar con validaciones CORREGIDAS
    @PostMapping
    public String guardarUsuario(@RequestParam("personaId") Long personaId,
                                 @RequestParam("usuario") String username,
                                 @RequestParam("clave") String clave,
                                 @RequestParam("rol") String rol,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        // ============================================
        // 1. VALIDAR NOMBRE DE USUARIO DUPLICADO
        // ============================================
        if (usuarioServicio.existeNombre(username)) {
            redirectAttributes.addFlashAttribute("error", 
                "❌ El nombre de usuario '" + username + "' ya está registrado");
            return "redirect:/registro";
        }

        // ============================================
        // 2. OBTENER PERSONA Y VERIFICAR
        // ============================================
        Persona persona = personaServicio.buscarPorId(personaId);
        if (persona == null) {
            redirectAttributes.addFlashAttribute("error", 
                "❌ Persona no encontrada");
            return "redirect:/registro";
        }

        // ⚠️ CORRECCIÓN: Verificar usuarioVinculado (login), NO usuario (creador)
        if (persona.getUsuarioVinculado() != null) {
            redirectAttributes.addFlashAttribute("error", 
                "❌ " + persona.getNombres() + " " + persona.getApellidos() + 
                " ya tiene una cuenta de usuario en el sistema");
            return "redirect:/registro";
        }

        // ============================================
        // 3. CREAR USUARIO SI PASÓ VALIDACIONES
        // ============================================
        Usuario nuevo = new Usuario();
        nuevo.setUsuario(username);
        nuevo.setClave(clave);
        nuevo.setRol(rol);
        nuevo.setEstado(true);
        nuevo.setPersona(persona);

                Long idGenerado = usuarioServicio.insertarUsuarioSP(
             nuevo.getUsuario(),
             nuevo.getClave(),
             nuevo.isEstado(),   // ✅ usar isEstado()
             nuevo.getRol(),
             persona
         );


        nuevo.setId(idGenerado);
        
        redirectAttributes.addFlashAttribute("mensaje", 
            "✅ Usuario '" + username + "' registrado exitosamente para " + 
            persona.getNombres() + " " + persona.getApellidos());
        return "redirect:/admin/principal";
    }
}