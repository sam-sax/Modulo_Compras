package com.example.demo.compras.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class FacturaDetalleDTO {
    private Long productoId;
    private BigDecimal cantidad;
    private BigDecimal precioUnitario;
    private Double tasaIva; // Para identificar si es 5, 10 o Exenta
}