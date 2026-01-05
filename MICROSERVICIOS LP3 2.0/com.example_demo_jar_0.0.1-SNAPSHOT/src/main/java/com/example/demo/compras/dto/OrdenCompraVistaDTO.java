package com.example.demo.compras.dto;

import com.example.demo.compras.EstadoOrden;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;


@Data
public class OrdenCompraVistaDTO {
    private Long id;
    private String proveedorNombre;
    private LocalDate fecha;
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal totalFinal;
    private EstadoOrden estado;
}
