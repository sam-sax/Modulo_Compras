package com.example.demo.servicios;

import com.example.demo.dto.VendedorDTO;
import com.example.demo.modelo.Vendedor;
import java.util.List;

public interface VendedorService {

    Vendedor registrar(VendedorDTO dto, String username);

    List<Vendedor> listarActivos();

    Vendedor buscarPorUsuario(String usuario);

    Vendedor registrarDesdeEntidad(Vendedor vendedor);
}
