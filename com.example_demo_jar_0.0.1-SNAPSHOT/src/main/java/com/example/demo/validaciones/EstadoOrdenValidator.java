package com.example.demo.validaciones;

import com.example.demo.compras.dto.EstadoOrdenDTO;
import com.example.demo.compras.EstadoOrden;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EstadoOrdenValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return EstadoOrdenDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EstadoOrdenDTO dto = (EstadoOrdenDTO) target;

        if (dto.getOrdenId() == null) {
            errors.rejectValue("ordenId", "ordenId.null", "El ID de la orden es obligatorio");
        }

        if (dto.getNuevoEstado() != null &&
            dto.getNuevoEstado() != EstadoOrden.CREADA &&
            dto.getNuevoEstado() != EstadoOrden.CONFIRMADA &&
            dto.getNuevoEstado() != EstadoOrden.ANULADA) {
            errors.rejectValue("nuevoEstado", "estado.invalid", "Estado no permitido");
        }
    }
}
