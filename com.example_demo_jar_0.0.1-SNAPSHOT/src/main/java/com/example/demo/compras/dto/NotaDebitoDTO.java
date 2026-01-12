package com.example.demo.compras.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class NotaDebitoDTO {
    private Long id;
    private LocalDate fecha;
    private BigDecimal montoSinIva;
    private BigDecimal iva;
    private BigDecimal montoTotal;
    private Long proveedorId;
}
