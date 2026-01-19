package com.example.demo.compras.servicio;

import com.example.demo.compras.PresupuestoItem;
import com.example.demo.compras.PresupuestoProveedor;
import com.example.demo.compras.dto.PresupuestoItemDTO;
import com.example.demo.compras.dto.PresupuestoProveedorDTO;
import com.example.demo.compras.repositorio.PresupuestoItemRepository; // Asegúrate de tener este repo
import com.example.demo.compras.repositorio.PresupuestoProveedorRepository;
import com.example.demo.compras.repositorio.ProductoRepository;
import com.example.demo.compras.repositorio.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PresupuestoProveedorServiceImpl implements PresupuestoProveedorService {

    @Autowired private PresupuestoProveedorRepository presupuestoRepo;
    @Autowired private ProveedorRepository proveedorRepo;
    @Autowired private ProductoRepository productoRepo;
    @Autowired private PresupuestoItemRepository itemRepo; // Agregado para guardar los detalles

    @Override
    public List<PresupuestoProveedor> listarTodos() {
        return presupuestoRepo.findAll();
    }

    @Override
    @Transactional
    public PresupuestoProveedor guardar(PresupuestoProveedorDTO dto) {
        PresupuestoProveedor p = new PresupuestoProveedor();
        p.setProveedor(proveedorRepo.findById(dto.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado")));
        
        p.setFecha(dto.getFecha());
        p.setNumero(dto.getNumero());
        p.setSubtotal(dto.getSubtotal());
        p.setIva(dto.getIva());
        p.setTotal(dto.getTotal());
        // p.setValidezHasta(dto.getValidezHasta()); // Descomenta si agregaste el campo a la Entidad
        p.setEstado(PresupuestoProveedor.EstadoPresupuesto.PENDIENTE);

        // Guardamos la cabecera primero
        PresupuestoProveedor guardado = presupuestoRepo.save(p);

        // Guardamos los ítems dinámicos
        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            for (PresupuestoItemDTO itemDto : dto.getItems()) {
                PresupuestoItem item = new PresupuestoItem();
                item.setPresupuesto(guardado);
                item.setProducto(productoRepo.findById(itemDto.getProductoId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado")));
                item.setCantidad(itemDto.getCantidad());
                item.setPrecioUnitario(itemDto.getPrecioUnitario());
                itemRepo.save(item);
            }
        }
        return guardado;
    }

    @Override
    public void eliminar(Long id) {
        if (presupuestoRepo.existsById(id)) {
            presupuestoRepo.deleteById(id);
        } else {
            throw new RuntimeException("No se puede eliminar: ID no existe");
        }
    }

    @Override
    public PresupuestoProveedor buscarPorId(Long id) {
        return presupuestoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Presupuesto no encontrado con ID: " + id));
    }
}