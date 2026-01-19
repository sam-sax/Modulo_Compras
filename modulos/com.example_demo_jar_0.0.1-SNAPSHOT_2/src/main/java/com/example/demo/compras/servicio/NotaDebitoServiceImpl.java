package com.example.demo.compras.servicio;

import com.example.demo.compras.*;
import com.example.demo.compras.dto.NotaDebitoDTO;
import com.example.demo.compras.dto.DetalleAjusteDTO; 
import com.example.demo.compras.repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotaDebitoServiceImpl implements NotaDebitoService {

    @Autowired private NotaDebitoRepository notaRepo;
    @Autowired private ProveedorRepository proveedorRepo;
    @Autowired private ProductoRepository productoRepo;
    @Autowired private NotaDebitoSPRepository notaSPRepo; 

    @Override
    @Transactional
    public NotaDebito registrarNota(NotaDebitoDTO dto) {
        // 1. Cabecera
        Proveedor proveedor = proveedorRepo.findById(dto.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        NotaDebito nota = new NotaDebito();
        nota.setProveedor(proveedor);
        nota.setFecha(dto.getFecha());
        nota.setMontoSinIva(dto.getMontoSinIva());
        nota.setIva(dto.getIva());
        nota.setMontoTotal(dto.getMontoTotal());
        // Inicializamos la lista de detalles (debe estar definida en la entidad NotaDebito)
        nota.setDetalles(new ArrayList<>());

        // 2. Procesar Detalles y Stock
        if (dto.getDetalles() != null) {
            for (DetalleAjusteDTO detDto : dto.getDetalles()) {
                // Buscamos el producto (Tu Repo usa Long)
                Producto producto = productoRepo.findById(detDto.getProductoId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                // En Nota de Débito, aumentamos stock si el cargo es por mercadería recibida extra
                producto.setStockActual(producto.getStockActual().add(detDto.getCantidad()));
                productoRepo.save(producto);

                // Crear el objeto detalle (Asegúrate de haber creado esta entidad)
                DetalleNotaDebito detalle = new DetalleNotaDebito();
                detalle.setNotaDebito(nota); // Vínculo padre-hijo
                detalle.setProducto(producto);
                detalle.setCantidad(detDto.getCantidad());
                
                nota.getDetalles().add(detalle);
            }
        }

        return notaRepo.save(nota);
    }

    @Override
    public List<NotaDebito> listarNotas() {
        return notaRepo.findAll();
    }

    @Override
    public List<NotaDebito> listarPorProveedor(Long proveedorId) {
        // Ejecución de tu Procedure: sp_notas_debito_proveedor
        return notaSPRepo.obtenerNotasDebitoPorProveedor(proveedorId);
    }
}