package com.example.demo.compras.servicio;

import com.example.demo.compras.LibroCompras;
import com.example.demo.compras.Compra;
import com.example.demo.compras.dto.LibroComprasDTO;
import com.example.demo.compras.repositorio.LibroComprasRepository;
import com.example.demo.compras.repositorio.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LibroComprasServiceImpl implements LibroComprasService {

    @Autowired private LibroComprasRepository libroRepo;
    @Autowired private CompraRepository compraRepo;

    @Override
    public LibroCompras registrarLibro(LibroComprasDTO dto) {
        Compra compra = compraRepo.findById(dto.getCompraId())
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

        LibroCompras libro = new LibroCompras();
        libro.setCompra(compra);
        libro.setTimbrado(dto.getTimbrado());
        libro.setNumeroFactura(dto.getNumeroFactura());
        libro.setBaseImponible(dto.getBaseImponible());
        libro.setIvaCredito(dto.getIvaCredito());
        libro.setTotalFactura(dto.getTotalFactura());

        return libroRepo.save(libro);
    }

    @Override
    public List<LibroCompras> listarLibros() {
        return libroRepo.findAll();
    }
}
