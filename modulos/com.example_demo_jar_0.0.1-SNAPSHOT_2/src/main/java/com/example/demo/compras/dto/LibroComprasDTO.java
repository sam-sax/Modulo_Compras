package com.example.demo.compras.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class LibroComprasDTO {
    private Long id;
    private String timbrado;
    private String numeroFactura;
    private BigDecimal baseImponible;
    private BigDecimal ivaCredito;
    private BigDecimal totalFactura;
    private Long compraId;
}
