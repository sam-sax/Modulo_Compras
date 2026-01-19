package com.example.demo.compras.servicio;

import com.example.demo.compras.PresupuestoProveedor;
import com.example.demo.compras.dto.PresupuestoProveedorDTO;
import java.util.List;

public interface PresupuestoProveedorService {
    List<PresupuestoProveedor> listarTodos();
    PresupuestoProveedor guardar(PresupuestoProveedorDTO dto);
    void eliminar(Long id);
   
    PresupuestoProveedor buscarPorId(Long id);
}