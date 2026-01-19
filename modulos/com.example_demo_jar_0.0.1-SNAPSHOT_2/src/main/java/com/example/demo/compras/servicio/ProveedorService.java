package com.example.demo.compras.servicio;

import com.example.demo.compras.Proveedor;
import com.example.demo.compras.dto.ProveedorDTO;
import java.util.List;

public interface ProveedorService {
    Proveedor registrarProveedor(ProveedorDTO dto);
    List<Proveedor> listarProveedores();
}