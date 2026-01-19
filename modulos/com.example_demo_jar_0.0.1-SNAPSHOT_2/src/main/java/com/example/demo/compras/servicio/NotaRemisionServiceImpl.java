package com.example.demo.compras.servicio;

import com.example.demo.compras.*;
import com.example.demo.compras.dto.NotaRemisionDTO;
import com.example.demo.compras.dto.DetalleAjusteDTO;
import com.example.demo.compras.repositorio.NotaRemisionRepository;
import com.example.demo.compras.repositorio.ProveedorRepository;
import com.example.demo.compras.repositorio.ProductoRepository;
import com.example.demo.modelo.Vendedor;
import com.example.demo.repositorio.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotaRemisionServiceImpl implements NotaRemisionService {

    @Autowired private NotaRemisionRepository remisionRepo;
    @Autowired private ProveedorRepository proveedorRepo;
    @Autowired private VendedorRepository vendedorRepo;
    @Autowired private ProductoRepository productoRepo;

    @Override
    @Transactional
    public NotaRemision registrarRemision(NotaRemisionDTO dto) {
        // 1. Cargar Cabecera
        Proveedor proveedor = proveedorRepo.findById(dto.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        Vendedor vendedor = vendedorRepo.findById(dto.getVendedorId())
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));

        NotaRemision remision = new NotaRemision();
        remision.setProveedor(proveedor);
        remision.setVendedor(vendedor);
        remision.setFecha(dto.getFecha());
        remision.setNumero(dto.getNumero());
        
        // Inicializar lista de detalles (Clase 13)
        remision.setDetalles(new ArrayList<>());

        // 2. Procesar Detalles y Actualizar Stock
        if (dto.getDetalles() != null) {
            for (DetalleAjusteDTO detDto : dto.getDetalles()) {
                // Buscamos el producto
                Producto producto = productoRepo.findById(detDto.getProductoId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                // Aumentamos el stock porque la mercadería está entrando físicamente
                producto.setStockActual(producto.getStockActual().add(detDto.getCantidad()));
                productoRepo.save(producto);

                // Crear entidad de detalle
                DetalleNotaRemision detalle = new DetalleNotaRemision();
                detalle.setNotaRemision(remision);
                detalle.setProducto(producto);
                detalle.setCantidad(detDto.getCantidad());
                
                remision.getDetalles().add(detalle);
            }
        }

        return remisionRepo.save(remision);
    }

    @Override
    public List<NotaRemision> listarRemisiones() {
        return remisionRepo.findAll();
    }

    @Override
    public NotaRemision buscarPorNumero(String numero) {
        return remisionRepo.findByNumero(numero);
    }
}