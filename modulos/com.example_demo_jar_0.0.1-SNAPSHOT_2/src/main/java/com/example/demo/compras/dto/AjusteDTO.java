package com.example.demo.compras.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class AjusteDTO {
    private Long id;
    private LocalDate fecha;
    private String motivo;
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal total;
    
    
    @NotEmpty(message = "El ajuste debe tener al menos un detalle")
    private List<@Valid DetalleAjusteDTO> detalles;
}
