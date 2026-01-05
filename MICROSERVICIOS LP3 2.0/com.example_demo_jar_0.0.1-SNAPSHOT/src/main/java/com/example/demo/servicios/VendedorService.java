package com.example.demo.servicios;

import com.example.demo.dto.VendedorDTO;
import com.example.demo.modelo.Vendedor;
import java.util.List;
import java.util.Optional;

public interface VendedorService {

    Vendedor registrar(VendedorDTO dto, String username); // username: quien lo crea

    List<Vendedor> listarActivos();
    
    // Servicio expone un método para buscar Vendedor por username
    Vendedor buscarPorUsuario(String usuario); // ✅ correcto
    
    
    Vendedor registrarDesdeEntidad(Vendedor vendedor);

    
    
}
