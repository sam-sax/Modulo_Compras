package com.example.demo.compras.servicio;

import com.example.demo.compras.Proveedor;
import com.example.demo.compras.repositorio.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    @Autowired private ProveedorRepository proveedorRepo;

    @Override
    public Proveedor registrarProveedor(Proveedor proveedor) {
        return proveedorRepo.save(proveedor);
    }

    @Override
    public List<Proveedor> listarProveedores() {
        return proveedorRepo.findAll();
    }
}
