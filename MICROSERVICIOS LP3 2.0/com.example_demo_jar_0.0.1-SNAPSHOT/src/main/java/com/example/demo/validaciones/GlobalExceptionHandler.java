package com.example.demo.validaciones;

import com.example.demo.modelo.Persona;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de errores de binding, como fechas inválidas
    @ExceptionHandler(org.springframework.web.bind.ServletRequestBindingException.class)
    public String handleBindingErrors(
            org.springframework.web.bind.ServletRequestBindingException ex,
            Model model) {

        model.addAttribute("fechaNacimientoError", "Fecha inválida");
        model.addAttribute("persona", new Persona());
        return "inicio"; // tu formulario de registro
    }

    // Manejo de errores al eliminar persona con usuario asociado
    @ExceptionHandler(Exception.class)
    public String handleGlobalException(Exception ex, Model model) {

        // Caso específico: persona con usuario asociado
        if (ex.getMessage() != null && ex.getMessage().contains("usuario asociado")) {
            model.addAttribute(
                "errorEliminar",
                "No se puede eliminar la persona porque tiene un usuario asociado"
            );
            return "listado"; // tu listado de personas
        }

        // Caso general: errores inesperados
        model.addAttribute(
            "errorMsg",
            ex.getMessage() != null ? ex.getMessage() : "Ocurrió un error inesperado"
        );
        return "inicio"; // por defecto vuelvo al registro
    }
}
