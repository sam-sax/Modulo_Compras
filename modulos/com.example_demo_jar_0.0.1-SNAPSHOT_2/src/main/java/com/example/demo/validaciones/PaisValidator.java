package com.example.demo.validaciones;

import com.example.demo.modelo.Pais;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PaisValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Pais.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Pais pais = (Pais) target;

        if (pais.getNombre() == null || pais.getNombre().trim().isEmpty()) {
            errors.rejectValue("nombre", "nombre.requerido", "El nombre del país es obligatorio");
        } else if (pais.getNombre().length() > 100) {
            errors.rejectValue("nombre", "nombre.largo", "Nombre del país demasiado largo (max 100)");
        }
    }
}
