package com.example.demo.compras.servicio;

import com.example.demo.compras.NotaDebito;
import com.example.demo.compras.Proveedor;
import com.example.demo.compras.dto.NotaDebitoDTO;
import com.example.demo.compras.repositorio.NotaDebitoRepository;
import com.example.demo.compras.repositorio.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotaDebitoServiceImpl implements NotaDebitoService {

    @Autowired private NotaDebitoRepository notaRepo;
    @Autowired private ProveedorRepository proveedorRepo;

    @Override
    public NotaDebito registrarNota(NotaDebitoDTO dto) {
        Proveedor proveedor = proveedorRepo.findById(dto.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        NotaDebito nota = new NotaDebito();
        nota.setProveedor(proveedor);
        nota.setFecha(dto.getFecha());
        nota.setMontoSinIva(dto.getMontoSinIva());
        nota.setIva(dto.getIva());
        nota.setMontoTotal(dto.getMontoTotal());

        return notaRepo.save(nota);
    }

    @Override
    public List<NotaDebito> listarNotas() {
        return notaRepo.findAll();
    }
}
