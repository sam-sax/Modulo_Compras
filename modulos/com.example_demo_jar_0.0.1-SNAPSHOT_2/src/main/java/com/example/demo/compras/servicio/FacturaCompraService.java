package com.example.demo.compras.servicio;

import com.example.demo.compras.FacturaCompra;
import com.example.demo.compras.dto.FacturaCompraDTO;
import java.util.List;

public interface FacturaCompraService {
    // Procesa la factura, calcula impuestos y guarda el detalle
    FacturaCompra guardarFactura(FacturaCompraDTO dto);
    
    // Recupera todas las facturas para el listado
    List<FacturaCompra> listarTodas();
    
    // Busca por ID si necesitas ver el detalle de una específica
    FacturaCompra buscarPorId(Long id);
}