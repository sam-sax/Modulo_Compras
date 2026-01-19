package com.example.demo.compras.servicio;

import com.example.demo.compras.Categoria;
import com.example.demo.compras.Producto;
import com.example.demo.compras.dto.ProductoDTO;
import com.example.demo.compras.repositorio.CategoriaRepository;
import com.example.demo.compras.repositorio.ProductoRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired 
    private ProductoRepository productoRepo;
    
    @Autowired
    private CategoriaRepository categoriaRepo;

    @Override
    @Transactional
    public Producto registrarProducto(ProductoDTO dto) {
        Producto producto = new Producto();
        
        // Mapeo de datos básicos
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setCodigoBarra(dto.getCodigoBarra());
        producto.setPrecio(dto.getPrecio());
        producto.setStockActual(dto.getStockActual());
        producto.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        
        // NUEVO: Seteo del campo IVA para cálculos de facturación
        producto.setIva(dto.getIva()); 

        // Manejo de Categoría
        if (dto.getCategoriaId() != null) {
            Categoria cat = categoriaRepo.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            producto.setCategoria(cat);
        }
        
        return productoRepo.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarProductos() {
        return productoRepo.findAll();
    }

    @Override
    public Producto buscarPorId(Long id) {
        return productoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public void actualizarStock(Long id, Double cantidad) {
        Producto p = productoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Obtenemos el stock actual de la Entidad
        BigDecimal actual = p.getStockActual() != null ? p.getStockActual() : BigDecimal.ZERO;

        // Sumamos la nueva cantidad de forma segura con BigDecimal
        BigDecimal nuevaCantidad = BigDecimal.valueOf(cantidad);
        p.setStockActual(actual.add(nuevaCantidad));

        productoRepo.save(p);
    }
}