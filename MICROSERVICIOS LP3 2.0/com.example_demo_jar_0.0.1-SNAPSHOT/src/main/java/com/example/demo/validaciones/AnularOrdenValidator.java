package com.example.demo.validaciones;

import com.example.demo.compras.dto.AnularOrdenDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AnularOrdenValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AnularOrdenDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AnularOrdenDTO dto = (AnularOrdenDTO) target;

        if (dto.getOrdenId() == null) {
            errors.rejectValue("ordenId", "ordenId.null", "El ID de la orden es obligatorio");
        }
    }
}
