package com.example.demo.compras.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class PresupuestoProveedorDTO {
    private Long id;
    private LocalDate fecha;
    private String numero;
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal total;
    private Long proveedorId;
    private LocalDate validezHasta; // Agregado para la validación de expiración
    private List<PresupuestoItemDTO> items; // Agregado para recibir la tabla dinámica
}