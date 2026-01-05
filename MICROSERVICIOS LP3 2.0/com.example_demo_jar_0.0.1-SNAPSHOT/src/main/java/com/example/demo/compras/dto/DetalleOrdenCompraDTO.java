package com.example.demo.compras.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class DetalleOrdenCompraDTO {
    @NotNull(message = "El producto es obligatorio")
    private Long productoId;

    @DecimalMin(value = "1.0", inclusive = true, message = "La cantidad debe ser al menos 1")
    private BigDecimal cantidad;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio unitario debe ser mayor a 0")
    private BigDecimal precioUnitario;
}
