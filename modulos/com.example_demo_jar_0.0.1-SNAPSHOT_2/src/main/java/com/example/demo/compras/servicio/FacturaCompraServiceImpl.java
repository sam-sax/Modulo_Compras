package com.example.demo.compras.servicio;

import com.example.demo.compras.FacturaCompra;
import com.example.demo.compras.FacturaDetalle;
import com.example.demo.compras.dto.FacturaCompraDTO;
import com.example.demo.compras.repositorio.FacturaCompraRepository;
import com.example.demo.compras.repositorio.ProveedorRepository;
import com.example.demo.compras.repositorio.ProductoRepository;
import com.example.demo.compras.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class FacturaCompraServiceImpl implements FacturaCompraService {

    @Autowired private FacturaCompraRepository facturaRepo;
    @Autowired private ProveedorRepository proveedorRepo;
    @Autowired private ProductoRepository productoRepo;

    @Override
@Transactional
public FacturaCompra guardarFactura(FacturaCompraDTO dto) {
    FacturaCompra factura = new FacturaCompra();
    factura.setNumeroFactura(dto.getNumeroFactura());
    factura.setFechaEmision(dto.getFechaEmision());
    factura.setCondicion(dto.getCondicion());
    
    factura.setProveedor(proveedorRepo.findById(dto.getProveedorId())
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado")));

    List<FacturaDetalle> detalles = new ArrayList<>();
    double sumaExenta = 0.0, sumaIva5 = 0.0, sumaIva10 = 0.0, sumaTotal = 0.0;

    for (var itemDto : dto.getItems()) {
        Producto producto = productoRepo.findById(itemDto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // 1. Crear detalle
        FacturaDetalle detalle = new FacturaDetalle();
        detalle.setProducto(producto);
        detalle.setCantidad(itemDto.getCantidad());
        detalle.setPrecioUnitario(itemDto.getPrecioUnitario());
        detalle.setFactura(factura);
        detalles.add(detalle);

        // 2. Cálculos financieros
        double subtotalItem = itemDto.getCantidad().doubleValue() * itemDto.getPrecioUnitario().doubleValue();
        sumaTotal += subtotalItem;

        // Lógica de IVA basada en el campo del Producto
        if (producto.getIva() == null || producto.getIva() == 0) {
            sumaExenta += subtotalItem;
        } else if (producto.getIva() == 5) {
            sumaIva5 += subtotalItem / 21.0;
        } else if (producto.getIva() == 10) {
            sumaIva10 += subtotalItem / 11.0;
        }

        // 3. Actualización de Stock Automática
        producto.setStockActual(producto.getStockActual().add(itemDto.getCantidad()));
        productoRepo.save(producto);
    }
    
    factura.setDetalles(detalles);
    factura.setTotalExenta(sumaExenta);
    factura.setTotalIva5(sumaIva5);
    factura.setTotalIva10(sumaIva10);
    factura.setTotalFinal(sumaTotal);
    
    return facturaRepo.save(factura);
}

    @Override
    public List<FacturaCompra> listarTodas() {
        return facturaRepo.findAll();
    }

    @Override
    public FacturaCompra buscarPorId(Long id) {
        return facturaRepo.findById(id).orElse(null);
    }
}