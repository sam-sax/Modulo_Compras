package com.example.demo.compras.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CuentaPagarDTO {
    private Long id;
    private LocalDate fechaVencimiento;
    private String estado; // PENDIENTE, PAGADA, PARCIAL
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal total;
    private Long proveedorId;
}
