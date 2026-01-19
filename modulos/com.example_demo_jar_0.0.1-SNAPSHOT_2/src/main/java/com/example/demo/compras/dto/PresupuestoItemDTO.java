package com.example.demo.compras.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PresupuestoItemDTO {
    private Long productoId;
    private Integer cantidad;
    private BigDecimal precioUnitario;
}