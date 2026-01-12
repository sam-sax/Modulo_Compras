package com.example.demo.compras.servicio;

import com.example.demo.compras.DetalleOrdenCompra;
import com.example.demo.compras.EstadoOrden;
import com.example.demo.compras.OrdenCompra;
import com.example.demo.compras.Producto;
import com.example.demo.compras.Proveedor;
import com.example.demo.compras.dto.DetalleOrdenCompraDTO;
import com.example.demo.compras.dto.EstadoOrdenDTO;
import com.example.demo.compras.dto.MovimientoStockVistaDTO;
import com.example.demo.compras.dto.OrdenCompraDTO;
import com.example.demo.compras.dto.OrdenCompraVistaDTO;
import com.example.demo.compras.repositorio.DetalleOrdenCompraRepository;
import com.example.demo.compras.repositorio.OrdenCompraRepository;
import com.example.demo.compras.repositorio.ProductoRepository;
import com.example.demo.compras.repositorio.ProveedorRepository;
import com.example.demo.stock.dto.MovimientoStockDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdenCompraServiceImpl implements OrdenCompraService {

    @Autowired private OrdenCompraRepository ordenRepo;
    @Autowired private DetalleOrdenCompraRepository detalleRepo;
    @Autowired private ProveedorRepository proveedorRepo;
    @Autowired private ProductoRepository productoRepo;
    @Autowired private WebClient.Builder webClientBuilder;

    // ============================
    // Registrar orden de compra
    // ============================
    @Override
    public OrdenCompra registrarOrden(OrdenCompraDTO dto) {
        Proveedor proveedor = proveedorRepo.findById(dto.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no existe"));

        OrdenCompra orden = new OrdenCompra();
        orden.setProveedor(proveedor);
        orden.setFecha(dto.getFecha());
        orden.setEstado(EstadoOrden.CREADA); // inicio en CREADA
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
            detalle.setSubtotal(d.getCantidad().multiply(d.getPrecioUnitario()));

            subtotal = subtotal.add(detalle.getSubtotal());
            detalleRepo.save(detalle);
        }

        BigDecimal iva = subtotal.multiply(new BigDecimal("0.10")).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal totalFinal = subtotal.add(iva).setScale(2, BigDecimal.ROUND_HALF_UP);

        saved.setSubtotal(subtotal);
        saved.setIva(iva);
        saved.setTotalFinal(totalFinal);

        return ordenRepo.save(saved);
    }

    // ============================
    // Listar órdenes de compra
    // ============================
    @Override
    public List<OrdenCompra> listarOrdenes() {
        return ordenRepo.findAll();
    }

    // ============================
    // Confirmar orden → generar stock
    // ============================
    public OrdenCompra confirmarOrden(Long ordenId) {
        OrdenCompra orden = ordenRepo.findById(ordenId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        if (orden.getEstado() != EstadoOrden.CREADA) {
            throw new RuntimeException("Solo se pueden confirmar órdenes en estado CREADA");
        }

        orden.setEstado(EstadoOrden.CONFIRMADA);
        ordenRepo.save(orden);

        for (DetalleOrdenCompra detalle : orden.getDetalles()) {
            MovimientoStockDTO movimiento = new MovimientoStockDTO();
            movimiento.setProductoId(detalle.getProducto().getId());
            movimiento.setCantidad(detalle.getCantidad());
            movimiento.setTipo("INGRESO"); // Compra → INGRESO
        }

        return orden;
    }

    // ============================
    // Anular orden
    // ============================
    public OrdenCompra anularOrden(Long ordenId) {
        OrdenCompra orden = ordenRepo.findById(ordenId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        if (orden.getEstado() == EstadoOrden.ANULADA) {
            throw new RuntimeException("La orden ya está anulada");
        }

        if (orden.getEstado() != EstadoOrden.CREADA && orden.getEstado() != EstadoOrden.CONFIRMADA) {
            throw new RuntimeException("Solo se pueden anular órdenes en estado CREADA o CONFIRMADA");
        }

        orden.setEstado(EstadoOrden.ANULADA);
        return ordenRepo.save(orden);
    }

                @Override
            public List<OrdenCompraVistaDTO> listarOrdenesFiltradas(LocalDate desde, LocalDate hasta, Long proveedorId) {
                List<OrdenCompra> ordenes = ordenRepo.findAll(); // Para simplicidad
                return ordenes.stream()
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
        dto.setTipo("INGRESO"); // Compra → INGRESO
        dto.setFecha(LocalDateTime.now()); // si tuvieras fecha real del movimiento, usarla
        dto.setOrdenId(orden.getId());
        return dto;
    }).toList();
    }
}
