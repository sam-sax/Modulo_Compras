package com.example.demo.compras.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PedidoItemDTO {
    private Long id;
    private Long productoId; // CAMBIO: Necesitamos el ID para buscarlo en la BD
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}