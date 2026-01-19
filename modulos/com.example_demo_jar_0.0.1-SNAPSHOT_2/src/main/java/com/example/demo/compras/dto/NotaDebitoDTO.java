package com.example.demo.compras.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class NotaDebitoDTO {
    private Long id;
    private LocalDate fecha;
    private BigDecimal montoSinIva;
    private BigDecimal iva;
    private BigDecimal montoTotal;
    private Long proveedorId;
    
    // AGREGAR ESTO: Sin esta lista, el Service no recibe los productos
    private List<DetalleAjusteDTO> detalles; 
}