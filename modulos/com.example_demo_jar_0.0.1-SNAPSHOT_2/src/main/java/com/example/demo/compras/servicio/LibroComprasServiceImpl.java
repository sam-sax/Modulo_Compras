package com.example.demo.compras.servicio;

import com.example.demo.compras.LibroCompras;
import com.example.demo.compras.Compra;
import com.example.demo.compras.dto.LibroComprasDTO;
import com.example.demo.compras.repositorio.LibroComprasRepository;
import com.example.demo.compras.repositorio.LibroComprasSPRepository; // Tu repo de SP
import com.example.demo.compras.repositorio.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class LibroComprasServiceImpl implements LibroComprasService {

    @Autowired private LibroComprasRepository libroRepo;
    @Autowired private CompraRepository compraRepo;
    @Autowired private LibroComprasSPRepository libroSPRepo; // Inyectamos el repo de procedimientos

    @Override
    public LibroCompras registrarLibro(LibroComprasDTO dto) {
        Compra compra = compraRepo.findById(dto.getCompraId())
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

        LibroCompras libro = new LibroCompras();
        libro.setCompra(compra);
        
        // Atributos de tu DTO
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

    @Override
    public BigDecimal obtenerIvaMensual(Integer mes, Integer anio) {
        // Lógica de tu SP: sp_reporte_iva
        return libroSPRepo.calcularIvaMensual(mes, anio);
    }

    @Override
    public List<LibroCompras> listarPorProveedor(Long proveedorId) {
        // Lógica de tu SP: sp_libro_compras_proveedor
        return libroSPRepo.obtenerLibroComprasPorProveedor(proveedorId);
    }
}