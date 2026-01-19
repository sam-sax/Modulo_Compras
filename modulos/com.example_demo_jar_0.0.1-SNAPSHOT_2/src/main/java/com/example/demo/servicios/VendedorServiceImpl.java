package com.example.demo.servicios;

import com.example.demo.dto.VendedorDTO;
import com.example.demo.modelo.Usuario;
import com.example.demo.modelo.Vendedor;
import com.example.demo.modelo.Persona;
import com.example.demo.repositorio.PersonaRepositorio;
import com.example.demo.repositorio.UsuarioRepositorio;
import com.example.demo.repositorio.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VendedorServiceImpl implements VendedorService {

    @Autowired private VendedorRepository vendedorRepo;
    @Autowired private PersonaRepositorio personaRepo;
    @Autowired private UsuarioRepositorio usuarioRepo;

    @Override
    public Vendedor registrar(VendedorDTO dto, String username) {
        Persona persona = personaRepo.findById(dto.getPersonaId())
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

        Usuario usuario = usuarioRepo.findByUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuario que crea no encontrado"));

        Vendedor vendedor = new Vendedor();
        vendedor.setPersona(persona);
        vendedor.setCodigoVendedor(dto.getCodigoVendedor());
        vendedor.setUsuario(usuario);

        return vendedorRepo.save(vendedor);
    }

    @Override
    public List<Vendedor> listarActivos() {
        return vendedorRepo.listarActivos();
    }

    @Override
    public Vendedor buscarPorUsuario(String usuario) {
        return vendedorRepo.findByUsuarioUsuario(usuario).orElse(null);
    }

    @Override
    public Vendedor registrarDesdeEntidad(Vendedor vendedor) {
        return vendedorRepo.save(vendedor);
    }
}
