package com.example.demo.compras.servicio;

import com.example.demo.compras.Producto;
import com.example.demo.compras.dto.ProductoDTO;
import java.util.List;

public interface ProductoService {
    List<Producto> listarProductos();
    Producto buscarPorId(Long id);
    void actualizarStock(Long id, Double cantidad); // Útil para Remisiones y Facturas
    
    Producto registrarProducto(ProductoDTO dto); 
}