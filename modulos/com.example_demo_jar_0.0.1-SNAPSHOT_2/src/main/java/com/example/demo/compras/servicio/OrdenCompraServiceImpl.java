package com.example.demo.compras.servicio;

import com.example.demo.compras.*;
import com.example.demo.compras.dto.*;
import com.example.demo.compras.repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdenCompraServiceImpl implements OrdenCompraService {

    @Autowired private OrdenCompraRepository ordenRepo;
    @Autowired private DetalleOrdenCompraRepository detalleRepo;
    @Autowired private ProveedorRepository proveedorRepo;
    @Autowired private ProductoRepository productoRepo;

    // ============================
    // Registrar orden de compra
    // ============================
    @Override
    @Transactional
    public OrdenCompra registrarOrden(OrdenCompraDTO dto) {
        Proveedor proveedor = proveedorRepo.findById(dto.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no existe"));

        OrdenCompra orden = new OrdenCompra();
        orden.setProveedor(proveedor);
        orden.setFecha(dto.getFecha());
        orden.setEstado(EstadoOrden.CREADA);
        orden.setDetalles(new ArrayList<>());
        
        // Primero guardamos la cabecera para tener el ID
        OrdenCompra saved = ordenRepo.save(orden);

        BigDecimal subtotal = BigDecimal.ZERO;

        for (DetalleOrdenCompraDTO d : dto.getDetalles()) {
            Producto producto = productoRepo.findById(d.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no existe"));

            DetalleOrdenCompra detalle = new DetalleOrdenCompra();
            detalle.setOrdenCompra(saved);
            detalle.setProducto(producto);
            detalle.setCantidad(d.getCantidad());
            detalle.setPrecioUnitario(d.getPrecioUnitario());
            
            BigDecimal filaSubtotal = d.getCantidad().multiply(d.getPrecioUnitario());
            detalle.setSubtotal(filaSubtotal);
            subtotal = subtotal.add(filaSubtotal);

            saved.getDetalles().add(detalle);
        }

        // Cálculos de IVA (10%) y Total
        BigDecimal iva = subtotal.multiply(new BigDecimal("0.10")).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalFinal = subtotal.add(iva).setScale(2, RoundingMode.HALF_UP);

        saved.setSubtotal(subtotal);
        saved.setIva(iva);
        saved.setTotalFinal(totalFinal);

        return ordenRepo.save(saved);
    }

    // ============================
    // Confirmar orden → Afecta Stock
    // ============================
    @Override
    @Transactional
    public OrdenCompra confirmarOrden(Long ordenId) {
        OrdenCompra orden = ordenRepo.findById(ordenId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        if (orden.getEstado() != EstadoOrden.CREADA) {
            throw new RuntimeException("Solo se pueden confirmar órdenes en estado CREADA");
        }

        // 1. Cambiar estado
        orden.setEstado(EstadoOrden.CONFIRMADA);

        // 2. Actualizar Stock Real (Lógica Clase 13)
        for (DetalleOrdenCompra detalle : orden.getDetalles()) {
            Producto producto = detalle.getProducto();
            // Aumentamos el stock actual del producto
            producto.setStockActual(producto.getStockActual().add(detalle.getCantidad()));
            productoRepo.save(producto);
        }

        return ordenRepo.save(orden);
    }

    @Override
    @Transactional
    public OrdenCompra anularOrden(Long ordenId) {
        OrdenCompra orden = ordenRepo.findById(ordenId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        if (orden.getEstado() == EstadoOrden.ANULADA) {
            throw new RuntimeException("La orden ya está anulada");
        }

        // Si ya estaba confirmada, habría que restar el stock nuevamente (opcional según regla de negocio)
        if (orden.getEstado() == EstadoOrden.CONFIRMADA) {
            for (DetalleOrdenCompra detalle : orden.getDetalles()) {
                Producto producto = detalle.getProducto();
                producto.setStockActual(producto.getStockActual().subtract(detalle.getCantidad()));
                productoRepo.save(producto);
            }
        }

        orden.setEstado(EstadoOrden.ANULADA);
        return ordenRepo.save(orden);
    }

    @Override
    public List<OrdenCompraVistaDTO> listarOrdenesFiltradas(LocalDate desde, LocalDate hasta, Long proveedorId) {
        return ordenRepo.findAll().stream()
                .filter(o -> (desde == null || !o.getFecha().isBefore(desde)) &&
                             (hasta == null || !o.getFecha().isAfter(hasta)) &&
                             (proveedorId == null || o.getProveedor().getId().equals(proveedorId)))
                .map(o -> {
                    OrdenCompraVistaDTO dto = new OrdenCompraVistaDTO();
                    dto.setId(o.getId());
                    dto.setProveedorNombre(o.getProveedor().getRazonSocial());
                    dto.setFecha(o.getFecha());
                    dto.setSubtotal(o.getSubtotal());
                    dto.setIva(o.getIva());
                    dto.setTotalFinal(o.getTotalFinal());
                    dto.setEstado(o.getEstado());
                    return dto;
                }).toList();
    }

    @Override
    public BigDecimal totalAcumulado(LocalDate desde, LocalDate hasta, Long proveedorId) {
        return listarOrdenesFiltradas(desde, hasta, proveedorId).stream()
                .map(OrdenCompraVistaDTO::getTotalFinal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<MovimientoStockVistaDTO> movimientosPorOrden(Long ordenId) {
        OrdenCompra orden = ordenRepo.findById(ordenId)
            .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        return orden.getDetalles().stream().map(det -> {
            MovimientoStockVistaDTO dto = new MovimientoStockVistaDTO();
            dto.setProductoId(det.getProducto().getId());
            dto.setProductoNombre(det.getProducto().getNombre());
            dto.setCantidad(det.getCantidad());
            dto.setTipo("INGRESO");
            dto.setFecha(LocalDateTime.now());
            dto.setOrdenId(orden.getId());
            return dto;
        }).toList();
    }

    @Override
    public List<OrdenCompra> listarOrdenes() {
        return ordenRepo.findAll();
    }
}