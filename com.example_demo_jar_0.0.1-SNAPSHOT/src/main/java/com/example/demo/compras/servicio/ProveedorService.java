package com.example.demo.compras.servicio;

import com.example.demo.compras.Proveedor;
import java.util.List;

public interface ProveedorService {
    Proveedor registrarProveedor(Proveedor proveedor);
    List<Proveedor> listarProveedores();
}
