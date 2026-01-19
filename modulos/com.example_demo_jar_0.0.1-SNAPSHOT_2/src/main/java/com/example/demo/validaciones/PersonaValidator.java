package com.example.demo.validaciones;

import com.example.demo.modelo.Persona;
import com.example.demo.modelo.TipoPersona;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonaValidator implements org.springframework.validation.Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Persona.class.equals(clazz);
    }

    @Override
    public void validate(Object target, org.springframework.validation.Errors errors) {
        Persona p = (Persona) target;

        // Limpiar datos según tipo de persona
        if (p.getTipoPersona() == TipoPersona.JURIDICA) {
            p.setEstadoCivil(null);
            p.setNombres(null);
            p.setApellidos(null);
            p.setNumeroCedula(null);
            p.setFechaNacimiento(null);
        } else { // FÍSICA
            p.setRazonSocial(null);
            p.setRepresentanteLegal(null);
            p.setRuc(null);
        }

        // Validaciones 
        if (p.getTipoPersona() == TipoPersona.FISICA) {
            if (p.getNombres() == null || p.getNombres().trim().isEmpty()) {
                errors.rejectValue("nombres", "nombres.requerido", "Por favor, ingrese los nombres de la persona.");
            }
            if (p.getApellidos() == null || p.getApellidos().trim().isEmpty()) {
                errors.rejectValue("apellidos", "apellidos.requerido", "Por favor, ingrese los apellidos.");
            }
            if (p.getNumeroCedula() == null || p.getNumeroCedula().trim().isEmpty()) {
                errors.rejectValue("numeroCedula", "numeroCedula.requerido", "La cédula es obligatoria.");
            }
            if (p.getFechaNacimiento() == null) {
                errors.rejectValue("fechaNacimiento", "fecha.requerida", "La fecha de nacimiento es obligatoria.");
            }
        } else { // JURIDICA
            if (p.getRazonSocial() == null || p.getRazonSocial().trim().isEmpty()) {
                errors.rejectValue("razonSocial", "razonSocial.requerido", "La razón social es obligatoria.");
            }
            if (p.getRuc() == null || p.getRuc().trim().isEmpty()) {
                errors.rejectValue("ruc", "ruc.requerido", "El RUC es obligatorio.");
            }
        }

        // Validación general de email
        if (p.getEmail() == null || p.getEmail().trim().isEmpty()) {
            errors.rejectValue("email", "email.requerido", "El correo electrónico es obligatorio.");
        } else if (!p.getEmail().matches("^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,6}$")) {
            errors.rejectValue("email", "email.invalido", "El correo electrónico no tiene un formato válido.");
        }

        // Teléfono
        if (p.getTelefono() == null || p.getTelefono().trim().isEmpty()) {
            errors.rejectValue("telefono", "telefono.requerido", "El teléfono es obligatorio.");
        }

        // Dirección
        if (p.getDireccion() != null && p.getDireccion().length() > 255) {
            errors.rejectValue("direccion", "direccion.largo", "La dirección es demasiado larga (máx. 255 caracteres).");
        }
    }
}
