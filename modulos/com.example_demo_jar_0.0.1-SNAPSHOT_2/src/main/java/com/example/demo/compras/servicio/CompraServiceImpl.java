package com.example.demo.compras.servicio;

import com.example.demo.compras.*;
import com.example.demo.compras.dto.CompraDTO;
import com.example.demo.compras.dto.DetalleCompraDTO;
import com.example.demo.compras.repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompraServiceImpl implements CompraService {

    @Autowired private CompraRepository compraRepo;
    @Autowired private OrdenCompraRepository ordenRepo;
    @Autowired private ProveedorRepository proveedorRepo;
    @Autowired private ProductoRepository productoRepo;
    @Autowired private CuentaPagarRepository cuentaRepo;
    @Autowired private LibroComprasRepository libroRepo;
    @Autowired private CompraSPRepository compraSPRepo;

    @Override
    @Transactional
    public Compra registrarCompra(CompraDTO dto) {
        Compra compra = new Compra();
        compra.setFecha(dto.getFecha());
        compra.setNumeroFactura(dto.getNumeroFactura()); // YA NO DARÁ ERROR
        
        Proveedor proveedor = proveedorRepo.findById(dto.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        compra.setProveedor(proveedor);

        if (dto.getOrdenCompraId() != null) {
            OrdenCompra orden = ordenRepo.findById(dto.getOrdenCompraId()).orElse(null);
            compra.setOrdenCompra(orden);
        }

        compra.setDetalles(new ArrayList<>());
        BigDecimal totalAcumulado = BigDecimal.ZERO;

        for (DetalleCompraDTO detDto : dto.getDetalles()) {
            Producto producto = productoRepo.findById(detDto.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // Actualización de Stock
            producto.setStockActual(producto.getStockActual().add(detDto.getCantidad()));
            productoRepo.save(producto);

            DetalleCompra detalle = new DetalleCompra();
            detalle.setCompra(compra);
            detalle.setProducto(producto);
            detalle.setCantidad(detDto.getCantidad());
            detalle.setPrecioUnitario(detDto.getPrecioUnitario());
            // El subtotal se calcula solo en el DetalleCompra por el @PrePersist que pusiste
            
            compra.getDetalles().add(detalle);
            totalAcumulado = totalAcumulado.add(detDto.getCantidad().multiply(detDto.getPrecioUnitario()));
        }

        compra.setSubtotal(totalAcumulado);
        compra.setIva(totalAcumulado.multiply(new BigDecimal("0.10")));
        compra.setTotal(compra.getSubtotal().add(compra.getIva()));

        Compra compraGuardada = compraRepo.save(compra);

        // Generar Cuenta a Pagar
        CuentaPagar deuda = new CuentaPagar();
        deuda.setCompra(compraGuardada);
        deuda.setProveedor(proveedor);
        deuda.setFechaVencimiento(compra.getFecha().plusDays(30));
        deuda.setEstado("PENDIENTE");
        deuda.setTotal(compra.getTotal());
        cuentaRepo.save(deuda);

        // Generar Libro Compras
        LibroCompras libro = new LibroCompras();
        libro.setCompra(compraGuardada);
        libro.setNumeroFactura(dto.getNumeroFactura());
        libro.setTotalFactura(compra.getTotal());
        libro.setIvaCredito(compra.getIva());
        libroRepo.save(libro);

        return compraGuardada;
    }

    @Override
    public List<Compra> listarCompras() {
        return compraRepo.findAll();
    }
    
    
            @Override
        @Transactional // Importante para ejecución de SP
        public void ejecutarCierre(LocalDate fecha) {
            compraSPRepo.ejecutarCierreCompras(fecha);
        }

}