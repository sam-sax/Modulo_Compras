package com.example.demo.servicios;

import com.example.demo.modelo.Comprador;
import com.example.demo.modelo.Persona;
import com.example.demo.repositorio.PersonaRepositorio;
import com.example.demo.repositorio.CompradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CompradorServiceImpl implements CompradorService {

    @Autowired 
    private CompradorRepository compradorRepo;

    @Autowired 
    private PersonaRepositorio personaRepo;

    @Override
    @Transactional
    public Comprador registrar(Comprador comprador) {
        // 1. Marcamos a la persona como comprador antes de guardar
        if (comprador.getPersona() != null) {
            Persona persona = comprador.getPersona();
            persona.setEsComprador(true);
            personaRepo.save(persona);
        }
        
        // 2. Guardamos la entidad Comprador
        return compradorRepo.save(comprador);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comprador> listarActivos() {
        return compradorRepo.listarActivos();
    }

    @Override
    public Comprador buscarPorUsuario(String usuario) {
        return compradorRepo.findByUsuarioUsuario(usuario).orElse(null);
    }
}