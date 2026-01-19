package com.example.demo.validaciones;

import com.example.demo.dto.PersonaDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Esto asegura que cualquier página tenga personaDTO
    @ModelAttribute("personaDTO")
    public PersonaDTO personaDTO() {
        return new PersonaDTO();
    }

    // Manejo específico de violaciones de integridad (FK, registros usados)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrity(DataIntegrityViolationException ex, Model model) {
        model.addAttribute("errorMsg", "Error de integridad: Este registro está siendo usado en otro módulo (Compras/Ventas).");
        model.addAttribute("personaDTO", new PersonaDTO()); // <-- asegura existencia
        return "error"; 
    }

    // Manejo global de cualquier excepción
    @ExceptionHandler(Exception.class)
    public String handleGlobalException(Exception ex, Model model) {
        String mensaje;

        if (ex.getMessage() != null && ex.getMessage().contains("usuario asociado")) {
            mensaje = "No se puede eliminar: Persona vinculada a un usuario activo.";
        } else {
            mensaje = "Error inesperado: " + (ex.getMessage() != null ? ex.getMessage() : "detalle no disponible");
        }

        model.addAttribute("errorMsg", mensaje);
        model.addAttribute("personaDTO", new PersonaDTO()); // <-- clave para Thymeleaf
        return "personas-form"; // renderiza tu página de inicio sin fallar
    }
}
