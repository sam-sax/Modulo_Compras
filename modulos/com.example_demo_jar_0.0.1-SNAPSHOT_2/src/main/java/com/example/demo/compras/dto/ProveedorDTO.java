package com.example.demo.compras.dto;

import lombok.Data;

@Data
public class ProveedorDTO {
    private Long id;
    private String razonSocial;
    private String ruc;
    private String direccion;
    private String telefono;
    private String email;
    private Boolean activo;
}