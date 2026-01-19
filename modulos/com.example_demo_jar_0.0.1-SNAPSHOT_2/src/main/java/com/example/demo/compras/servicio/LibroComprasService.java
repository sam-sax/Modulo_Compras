package com.example.demo.compras.servicio;

import com.example.demo.compras.LibroCompras;
import com.example.demo.compras.dto.LibroComprasDTO;
import java.math.BigDecimal;
import java.util.List;

public interface LibroComprasService {
    LibroCompras registrarLibro(LibroComprasDTO dto);
    List<LibroCompras> listarLibros();
    // Métodos para  Stored Procedures de reportes
    BigDecimal obtenerIvaMensual(Integer mes, Integer anio);
    List<LibroCompras> listarPorProveedor(Long proveedorId);
}