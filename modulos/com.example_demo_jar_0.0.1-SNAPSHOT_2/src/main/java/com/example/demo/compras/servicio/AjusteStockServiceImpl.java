package com.example.demo.compras.servicio;

import com.example.demo.compras.AjusteStock;
import com.example.demo.compras.Producto;
import com.example.demo.compras.dto.AjusteStockDTO;
import com.example.demo.compras.repositorio.AjusteStockRepository;
import com.example.demo.compras.repositorio.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AjusteStockServiceImpl implements AjusteStockService {

    @Autowired private AjusteStockRepository ajusteRepo;
    @Autowired private ProductoRepository productoRepo;

    @Override
    @Transactional
    public void procesarAjuste(AjusteStockDTO dto) {
        Producto producto = productoRepo.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        AjusteStock ajuste = new AjusteStock();
        ajuste.setProducto(producto);
        ajuste.setCantidad(dto.getCantidad());
        ajuste.setMotivo(dto.getMotivo());
        ajuste.setTipo(dto.getTipo()); // "ENTRADA" o "SALIDA"
        ajuste.setFecha(LocalDateTime.now());

        // Lógica de actualización de stock
        if ("ENTRADA".equalsIgnoreCase(dto.getTipo())) {
            producto.setStockActual(producto.getStockActual().add(dto.getCantidad()));
        } else {
            producto.setStockActual(producto.getStockActual().subtract(dto.getCantidad()));
        }

        productoRepo.save(producto);
        ajusteRepo.save(ajuste);
    }

    @Override
    public List<AjusteStock> listarAjustes() {
        return ajusteRepo.findAll();
    }
}