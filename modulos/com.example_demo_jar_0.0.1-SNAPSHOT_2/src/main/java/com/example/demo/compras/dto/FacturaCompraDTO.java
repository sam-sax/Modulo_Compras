package com.example.demo.compras.dto;

import com.example.demo.compras.CondicionPago; // Importamos el Enum
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class FacturaCompraDTO {
    private String numeroFactura;
    private Long proveedorId;
    private LocalDate fechaEmision; // Cambiado de 'fecha' para coincidir con la Entidad
    private CondicionPago condicion; // Cambiado de String a CondicionPago
    private List<FacturaDetalleDTO> items;
}