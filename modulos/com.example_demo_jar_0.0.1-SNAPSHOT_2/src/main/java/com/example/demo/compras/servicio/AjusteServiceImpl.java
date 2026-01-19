package com.example.demo.compras.servicio;

import com.example.demo.compras.Ajuste;
import com.example.demo.compras.DetalleAjuste;
import com.example.demo.compras.Producto;
import com.example.demo.compras.TipoAjuste;
import com.example.demo.compras.dto.AjusteDTO;
import com.example.demo.compras.dto.DetalleAjusteDTO;
import com.example.demo.compras.repositorio.AjusteRepository;
import com.example.demo.compras.repositorio.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AjusteServiceImpl implements AjusteService {

    @Autowired private AjusteRepository ajusteRepo;
    @Autowired private ProductoRepository productoRepo;

    @Override
    @Transactional // Para que si falla un producto, no se guarde nada
    public Ajuste registrarAjuste(AjusteDTO dto) {
        // 1. Creamos la Cabecera
        Ajuste ajuste = new Ajuste();
        ajuste.setFecha(dto.getFecha());
        ajuste.setMotivo(dto.getMotivo());
        ajuste.setDetalles(new ArrayList<>()); // Inicializamos la lista de detalles

        // 2. Recorremos los detalles que vienen en el DTO (Lógica Clase 13)
        for (DetalleAjusteDTO detDto : dto.getDetalles()) {
            Producto producto = productoRepo.findById(detDto.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // --- LÓGICA DE STOCK ---
            if (detDto.getTipo() == TipoAjuste.ENTRADA) {
                producto.setStockActual(producto.getStockActual().add(detDto.getCantidad()));
            } else {
                producto.setStockActual(producto.getStockActual().subtract(detDto.getCantidad()));
            }
            productoRepo.save(producto); // Actualizamos el stock del producto

            // 3. Creamos el objeto Detalle y lo vinculamos a la cabecera
            DetalleAjuste detalle = new DetalleAjuste();
            detalle.setAjuste(ajuste); // Vínculo con el padre
            detalle.setProducto(producto);
            detalle.setCantidad(detDto.getCantidad());
            detalle.setTipo(detDto.getTipo());

            ajuste.getDetalles().add(detalle);
        }

        // 4. Guardamos la cabecera (por Cascade se guardan los detalles)
        return ajusteRepo.save(ajuste);
    }

    @Override
    public List<Ajuste> listarAjustes() {
        return ajusteRepo.findAll();
    }
}