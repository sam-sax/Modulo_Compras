package com.example.demo.compras.servicio;

import com.example.demo.compras.*;
import com.example.demo.compras.dto.NotaCreditoDTO;
import com.example.demo.compras.dto.DetalleAjusteDTO;
import com.example.demo.compras.repositorio.NotaCreditoRepository;
import com.example.demo.compras.repositorio.ProveedorRepository;
import com.example.demo.compras.repositorio.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotaCreditoServiceImpl implements NotaCreditoService {

    @Autowired private NotaCreditoRepository notaRepo;
    @Autowired private ProveedorRepository proveedorRepo;
    @Autowired private ProductoRepository productoRepo;

    @Override
    @Transactional
    public NotaCredito registrarNota(NotaCreditoDTO dto) {
        // 1. Crear Cabecera
        Proveedor proveedor = proveedorRepo.findById(dto.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        NotaCredito nota = new NotaCredito();
        nota.setProveedor(proveedor);
        nota.setFecha(dto.getFecha());
        nota.setMontoSinIva(dto.getMontoSinIva());
        nota.setIva(dto.getIva());
        nota.setMontoTotal(dto.getMontoTotal());
        // Inicializamos la lista de detalles definida en la entidad
        nota.setDetalles(new ArrayList<>()); 

        // 2. Procesar Detalles y Actualizar Stock
        if (dto.getDetalles() != null) {
            for (DetalleAjusteDTO detDto : dto.getDetalles()) {
                // Buscamos el producto (Tu Repo usa Long, por lo que pasamos el ID directo)
                Producto producto = productoRepo.findById(detDto.getProductoId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                // Descontar del stock (Al ser nota de crédito por devolución, restamos lo que entró)
                producto.setStockActual(producto.getStockActual().subtract(detDto.getCantidad()));
                productoRepo.save(producto);

                // Crear el objeto de detalle (Usa el nombre exacto de tu entidad del Paso 1)
                DetalleNotaCredito detalle = new DetalleNotaCredito();
                detalle.setNotaCredito(nota); // Relación obligatoria para @OneToMany
                detalle.setProducto(producto);
                detalle.setCantidad(detDto.getCantidad());
                
                // Agregamos el detalle a la lista de la cabecera
                nota.getDetalles().add(detalle);
            }
        }

        // 3. Guardar cabecera (Esto guarda los detalles automáticamente por el CascadeType.ALL)
        return notaRepo.save(nota);
    }

    @Override
    public List<NotaCredito> listarNotas() {
        return notaRepo.findAll();
    }
}