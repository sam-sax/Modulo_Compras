package com.example.demo.compras.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class NotaRemisionDTO {
    private Long id;
    private LocalDate fecha;
    private String numero;
    private Long proveedorId;
    private Long vendedorId;
}
