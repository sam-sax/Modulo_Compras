package com.example.demo.compras.servicio;

import com.example.demo.compras.Compra;
import com.example.demo.compras.dto.CompraDTO;
import java.time.LocalDate;
import java.util.List;

public interface CompraService {
    Compra registrarCompra(CompraDTO dto);
    List<Compra> listarCompras();
    
    // Dentro de CompraService.java
    void ejecutarCierre(LocalDate fecha);
}