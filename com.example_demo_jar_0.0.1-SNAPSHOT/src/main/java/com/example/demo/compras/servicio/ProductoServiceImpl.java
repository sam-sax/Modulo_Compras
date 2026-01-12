package com.example.demo.compras.servicio;

import com.example.demo.compras.Producto;
import com.example.demo.compras.repositorio.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired private ProductoRepository productoRepo;

    @Override
    public Producto registrarProducto(Producto producto) {
        return productoRepo.save(producto);
    }

    @Override
    public List<Producto> listarProductos() {
        return productoRepo.findAll();
    }
}
