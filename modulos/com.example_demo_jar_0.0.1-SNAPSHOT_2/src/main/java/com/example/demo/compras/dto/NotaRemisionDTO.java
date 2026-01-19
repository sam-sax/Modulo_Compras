package com.example.demo.compras.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class NotaRemisionDTO {
    private Long id;
    private String numero;
    private LocalDate fecha;
    private Long proveedorId;
    private Long vendedorId;
    private List<DetalleAjusteDTO> detalles; // Usamos tu DTO común de detalles
}