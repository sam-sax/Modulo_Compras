package com.example.demo.validaciones;

import com.example.demo.modelo.Ciudad;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CiudadValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Ciudad.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Ciudad ciudad = (Ciudad) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "nombre.requerido", "El nombre de la ciudad es obligatorio");
        
        if (ciudad.getNombre() != null && ciudad.getNombre().length() > 100) {
            errors.rejectValue("nombre", "nombre.largo", "Máximo 100 caracteres");
        }

        if (ciudad.getPais() == null) {
            errors.rejectValue("pais", "pais.requerido", "Debe estar vinculada a un país");
        }
    }
}