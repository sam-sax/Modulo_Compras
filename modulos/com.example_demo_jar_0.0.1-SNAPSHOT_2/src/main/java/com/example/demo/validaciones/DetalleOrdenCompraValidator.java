package com.example.demo.validaciones;

import com.example.demo.compras.dto.DetalleOrdenCompraDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.math.BigDecimal;

@Component
public class DetalleOrdenCompraValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return DetalleOrdenCompraDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DetalleOrdenCompraDTO dto = (DetalleOrdenCompraDTO) target;

        if (dto.getProductoId() == null) {
            errors.rejectValue("productoId", "productoId.null", "El producto es obligatorio");
        }

        if (dto.getCantidad() == null || dto.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
            errors.rejectValue("cantidad", "cantidad.invalid", "La cantidad debe ser mayor a 0");
        }

        if (dto.getPrecioUnitario() == null || dto.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            errors.rejectValue("precioUnitario", "precioUnitario.invalid", "El precio unitario debe ser mayor a 0");
        }
    }
}
