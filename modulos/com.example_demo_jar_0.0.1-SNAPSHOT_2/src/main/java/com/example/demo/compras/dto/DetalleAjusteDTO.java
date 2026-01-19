package com.example.demo.compras.dto;

import com.example.demo.compras.TipoAjuste;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class DetalleAjusteDTO {

    @NotNull(message = "El producto es obligatorio")
    private Long productoId;

    @DecimalMin(value = "0.1", message = "La cantidad debe ser mayor a 0")
    private BigDecimal cantidad;

    @NotNull(message = "El tipo de ajuste (ENTRADA/SALIDA) es obligatorio")
    private TipoAjuste tipo; 
}