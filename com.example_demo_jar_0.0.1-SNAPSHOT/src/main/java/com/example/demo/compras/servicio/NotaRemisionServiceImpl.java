package com.example.demo.compras.servicio;

import com.example.demo.compras.NotaRemision;
import com.example.demo.compras.Proveedor;

import com.example.demo.compras.dto.NotaRemisionDTO;
import com.example.demo.compras.repositorio.NotaRemisionRepository;
import com.example.demo.compras.repositorio.ProveedorRepository;

import com.example.demo.modelo.Vendedor;
import com.example.demo.repositorio.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotaRemisionServiceImpl implements NotaRemisionService {

    @Autowired private NotaRemisionRepository remisionRepo;
    @Autowired private ProveedorRepository proveedorRepo;
    @Autowired private VendedorRepository vendedorRepo;

    @Override
    public NotaRemision registrarRemision(NotaRemisionDTO dto) {
        Proveedor proveedor = proveedorRepo.findById(dto.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        Vendedor vendedor = vendedorRepo.findById(dto.getVendedorId())
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));

        NotaRemision remision = new NotaRemision();
        remision.setProveedor(proveedor);
        remision.setVendedor(vendedor); // ✅ ahora asignamos el objeto Vendedor
        remision.setFecha(dto.getFecha());
        remision.setNumero(dto.getNumero());

        return remisionRepo.save(remision);
    }

    @Override
    public List<NotaRemision> listarRemisiones() {
        return remisionRepo.findAll();
    }
}
