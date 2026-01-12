package com.example.demo.compras.servicio;

import com.example.demo.compras.Producto;
import java.util.List;

public interface ProductoService {
    Producto registrarProducto(Producto producto);
    List<Producto> listarProductos();
}
