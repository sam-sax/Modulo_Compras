package com.example.demo.compras.servicio;

import com.example.demo.compras.Compra;
import com.example.demo.compras.OrdenCompra;
import com.example.demo.compras.dto.CompraDTO;
import com.example.demo.compras.repositorio.CompraRepository;
import com.example.demo.compras.repositorio.OrdenCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CompraServiceImpl implements CompraService {

    @Autowired private CompraRepository compraRepo;
    @Autowired private OrdenCompraRepository ordenRepo;

    @Override
    public Compra registrarCompra(CompraDTO dto) {
        OrdenCompra orden = ordenRepo.findById(dto.getOrdenCompraId())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        Compra compra = new Compra();
        compra.setOrdenCompra(orden);
        compra.setFecha(dto.getFecha());
        compra.setSubtotal(dto.getSubtotal());
        compra.setIva(dto.getIva());
        compra.setTotal(dto.getTotal());

        return compraRepo.save(compra);
    }

    @Override
    public List<Compra> listarCompras() {
        return compraRepo.findAll();
    }
}
