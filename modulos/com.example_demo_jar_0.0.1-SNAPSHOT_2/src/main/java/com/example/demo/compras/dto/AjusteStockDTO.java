package com.example.demo.compras.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AjusteStockDTO {
    private Long productoId;
    private BigDecimal cantidad;
    private String tipo; // ENTRADA o SALIDA
    private String motivo;
}