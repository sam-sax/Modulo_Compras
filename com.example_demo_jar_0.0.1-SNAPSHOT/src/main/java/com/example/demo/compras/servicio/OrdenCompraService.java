package com.example.demo.compras.servicio;

import com.example.demo.compras.OrdenCompra;
import com.example.demo.compras.dto.MovimientoStockVistaDTO;
import com.example.demo.compras.dto.OrdenCompraDTO;
import com.example.demo.compras.dto.OrdenCompraVistaDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrdenCompraService {
    // Registrar nueva orden
    OrdenCompra registrarOrden(OrdenCompraDTO dto);

    // Listar todas las órdenes
    List<OrdenCompra> listarOrdenes();

    // Confirmar orden
    OrdenCompra confirmarOrden(Long ordenId);

    // Anular orden
    OrdenCompra anularOrden(Long ordenId);
    
    
    
    
    // NUEVOS MÉTODOS
    List<OrdenCompraVistaDTO> listarOrdenesFiltradas(LocalDate desde, LocalDate hasta, Long proveedorId);
    BigDecimal totalAcumulado(LocalDate desde, LocalDate hasta, Long proveedorId);
    List<MovimientoStockVistaDTO> movimientosPorOrden(Long ordenId);
    
    
    
}
