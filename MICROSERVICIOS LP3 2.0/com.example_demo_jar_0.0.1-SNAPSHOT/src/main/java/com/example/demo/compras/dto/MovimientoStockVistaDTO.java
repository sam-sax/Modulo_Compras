package com.example.demo.compras.dto;

import com.example.demo.compras.EstadoOrden;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MovimientoStockVistaDTO {
    private Long productoId;
    private String productoNombre;
    private BigDecimal cantidad;
    private String tipo; // INGRESO/EGRESO
    private LocalDateTime fecha;
    private Long ordenId; // referencia a la orden
}
