package com.example.demo.compras.dto;

import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrdenCompraDTO {

    @NotNull(message = "El proveedor es obligatorio")
    private Long proveedorId;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    // Subtotal calculado a partir de los detalles
    private BigDecimal subtotal = BigDecimal.ZERO;

    // IVA calculado sobre el subtotal
    private BigDecimal iva = BigDecimal.ZERO;

    @DecimalMin(value = "0.0", inclusive = false, message = "El total debe ser mayor a 0")
    private BigDecimal total = BigDecimal.ZERO;

    @NotEmpty(message = "Debe incluir al menos un detalle")
    private List<@Valid DetalleOrdenCompraDTO> detalles;
}
