package com.example.demo.validaciones;

import com.example.demo.modelo.Persona;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonaValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Persona.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Persona p = (Persona) target;

        // NOMBRES (requerido)
        if (p.getNombres() == null || p.getNombres().trim().isEmpty()) {
            errors.rejectValue("nombres", "nombres.requerido", "Los nombres son obligatorios");
        } else if (!ValidacionesGenerales.esNombreValido(p.getNombres())) {
            errors.rejectValue("nombres", "nombres.invalido",
                    "Nombre inválido: solo letras y espacios, máximo 50 caracteres");
        }

        // APELLIDOS (requerido)
        if (p.getApellidos() == null || p.getApellidos().trim().isEmpty()) {
            errors.rejectValue("apellidos", "apellidos.requerido", "Los apellidos son obligatorios");
        } else if (!ValidacionesGenerales.esNombreValido(p.getApellidos())) {
            errors.rejectValue("apellidos", "apellidos.invalido",
                    "Apellidos inválidos: solo letras y espacios, máximo 50 caracteres");
        }

        // TELEFONO (requerido)
        if (p.getTelefono() == null || p.getTelefono().trim().isEmpty()) {
            errors.rejectValue("telefono", "telefono.requerido", "El teléfono es obligatorio");
        } else if (!ValidacionesGenerales.esTelefonoValido(p.getTelefono())) {
            errors.rejectValue("telefono", "telefono.invalido", "Teléfono inválido: solo números, 8-15 dígitos");
        }

        // DIRECCION (opcional pero si existe, validar)
        if (p.getDireccion() != null && !p.getDireccion().trim().isEmpty()) {
            if (!ValidacionesGenerales.esDireccionValida(p.getDireccion())) {
                errors.rejectValue("direccion", "direccion.invalida",
                        "Dirección inválida: máximo 100 caracteres");
            }
        }

        // FECHA NACIMIENTO (requerida)
        if (p.getFechaNacimiento() == null) {
            errors.rejectValue("fechaNacimiento", "fecha.requerida", "La fecha de nacimiento es obligatoria");
        } else if (!ValidacionesGenerales.esFechaValida(p.getFechaNacimiento())) {
            errors.rejectValue("fechaNacimiento", "fecha.invalida",
                    "Fecha inválida: debe ser una fecha pasada y mayor a 1900");
        }

        // EMAIL (requerido)
        if (p.getEmail() == null || p.getEmail().trim().isEmpty()) {
            errors.rejectValue("email", "email.requerido", "El email es obligatorio");
        } else if (!ValidacionesGenerales.esEmailValido(p.getEmail())) {
            errors.rejectValue("email", "email.invalido", "Formato de email inválido");
        }

        // ESTADO CIVIL (opcional pero validar longitud)
        if (p.getEstadoCivil() != null && p.getEstadoCivil().length() > 50) {
            errors.rejectValue("estadoCivil", "estadoCivil.largo", "Estado civil demasiado largo (max 50)");
        }

        // CIUDAD y PAIS (opcional pero validar longitud)
        if (p.getCiudad() != null && p.getCiudad().length() > 100) {
            errors.rejectValue("ciudad", "ciudad.largo", "Ciudad demasiado larga (max 100)");
        }
        if (p.getPais() != null && p.getPais().length() > 100) {
            errors.rejectValue("pais", "pais.largo", "País demasiado largo (max 100)");
        }

        // NUMERO CEDULA (requerido)
        if (p.getNumeroCedula() == null || p.getNumeroCedula().trim().isEmpty()) {
            errors.rejectValue("numeroCedula", "numeroCedula.requerido", "Número de cédula es obligatorio");
        } else if (!ValidacionesGenerales.esCedulaValida(p.getNumeroCedula())) {
            errors.rejectValue("numeroCedula", "numeroCedula.invalido", "Número de cédula inválido");
        }

        // RUC (opcional pero si existe validar)
        if (p.getRuc() != null && !p.getRuc().trim().isEmpty()) {
            if (!ValidacionesGenerales.esRucValido(p.getRuc())) {
                errors.rejectValue("ruc", "ruc.invalido", "RUC inválido");
            }
        }

        // NOTA: comprobaciones de unicidad (email / teléfono / cédula) en el controlador
    }
}
