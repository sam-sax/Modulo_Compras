package com.example.demo.compras.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class DetalleCompraDTO {

    @NotNull(message = "El producto es obligatorio")
    private Long productoId;

    @DecimalMin(value = "0.1", message = "La cantidad debe ser mayor a 0")
    private BigDecimal cantidad;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private BigDecimal precioUnitario;

    private BigDecimal subtotal; // Opcional, se puede calcular en el servidor
}