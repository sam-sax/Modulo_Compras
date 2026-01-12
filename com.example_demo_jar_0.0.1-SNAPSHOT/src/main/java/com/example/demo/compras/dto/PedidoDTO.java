package com.example.demo.compras.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class PedidoDTO {
    private Long id;
    private LocalDate fecha;
    private String estado; // BORRADOR, APROBADO, ANULADO
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal total;
    private Long compradorId;
    private Long proveedorId;
    private List<PedidoItemDTO> items;
}
