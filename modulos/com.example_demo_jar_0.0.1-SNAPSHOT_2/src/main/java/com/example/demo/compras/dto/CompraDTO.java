package com.example.demo.compras.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CompraDTO {
    private Long id;
    private LocalDate fecha;
    private String numeroFactura;
    private Long proveedorId; 
    private Long ordenCompraId; 
    
    // Sincronizado con el Service y el HTML
    private List<DetalleCompraDTO> detalles = new ArrayList<>(); 
    
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal total;
}