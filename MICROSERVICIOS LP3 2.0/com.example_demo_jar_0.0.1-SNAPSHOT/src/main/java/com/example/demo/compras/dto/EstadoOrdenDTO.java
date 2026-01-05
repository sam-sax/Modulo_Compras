package com.example.demo.compras.dto;

import com.example.demo.compras.EstadoOrden;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class EstadoOrdenDTO {
    private Long ordenId;
    private EstadoOrden nuevoEstado; // CONFIRMADA o ANULADA
}
