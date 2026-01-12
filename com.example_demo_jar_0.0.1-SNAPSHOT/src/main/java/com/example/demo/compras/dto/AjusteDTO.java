package com.example.demo.compras.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AjusteDTO {
    private Long id;
    private LocalDate fecha;
    private String motivo;
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal total;
}
