package com.example.demo.compras.servicio;

import com.example.demo.compras.NotaCredito;
import com.example.demo.compras.Proveedor;
import com.example.demo.compras.dto.NotaCreditoDTO;
import com.example.demo.compras.repositorio.NotaCreditoRepository;
import com.example.demo.compras.repositorio.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotaCreditoServiceImpl implements NotaCreditoService {

    @Autowired private NotaCreditoRepository notaRepo;
    @Autowired private ProveedorRepository proveedorRepo;

    @Override
    public NotaCredito registrarNota(NotaCreditoDTO dto) {
        Proveedor proveedor = proveedorRepo.findById(dto.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        NotaCredito nota = new NotaCredito();
        nota.setProveedor(proveedor);
        nota.setFecha(dto.getFecha());
        nota.setMontoSinIva(dto.getMontoSinIva());
        nota.setIva(dto.getIva());
        nota.setMontoTotal(dto.getMontoTotal());

        return notaRepo.save(nota);
    }

    @Override
    public List<NotaCredito> listarNotas() {
        return notaRepo.findAll();
    }
}
