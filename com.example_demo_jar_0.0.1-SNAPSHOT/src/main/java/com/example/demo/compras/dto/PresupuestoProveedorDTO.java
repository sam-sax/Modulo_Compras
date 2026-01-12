package com.example.demo.compras.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PresupuestoProveedorDTO {
    private Long id;
    private LocalDate fecha;
    private String numero;
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal total;
    private Long proveedorId;
}
