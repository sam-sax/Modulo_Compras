package com.example.demo.compras.repositorio;

import com.example.demo.compras.LibroCompras;
import java.math.BigDecimal;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface LibroComprasSPRepository extends CrudRepository<LibroCompras, Long> {

    // Stored Procedure: sp_reporte_iva_mensual
    @Procedure(procedureName = "sp_reporte_iva_mensual")
    BigDecimal calcularIvaMensual(Integer mes, Integer anio);

    // Stored Procedure: sp_libro_compras_proveedor
    @Procedure(procedureName = "sp_libro_compras_proveedor")
    List<LibroCompras> obtenerLibroComprasPorProveedor(Long proveedorId);
}
