package com.example.demo.validaciones;

import com.example.demo.compras.dto.OrdenCompraDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class OrdenCompraValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return OrdenCompraDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OrdenCompraDTO dto = (OrdenCompraDTO) target;

        if (dto.getProveedorId() == null) {
            errors.rejectValue("proveedorId", "proveedorId.null", "El proveedor es obligatorio");
        }

        if (dto.getFecha() == null) {
            errors.rejectValue("fecha", "fecha.null", "La fecha es obligatoria");
        }

        if (dto.getTotal() == null || dto.getTotal().doubleValue() <= 0) {
            errors.rejectValue("total", "total.invalid", "El total debe ser mayor a 0");
        }

        if (dto.getDetalles() == null || dto.getDetalles().isEmpty()) {
            errors.rejectValue("detalles", "detalles.empty", "Debe incluir al menos un detalle");
        }
    }
}
