package com.example.demo.compras.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String codigoBarra;
    private BigDecimal precio;
    private BigDecimal stockActual;
    private Boolean activo;
    private Long categoriaId;
    private Integer iva; // 0, 5 o 10
}