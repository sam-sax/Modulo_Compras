package com.example.demo.servicios;

import com.example.demo.modelo.Ciudad;
import com.example.demo.modelo.Pais;
import com.example.demo.repositorio.CiudadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CiudadServiceImpl implements CiudadService {

    @Autowired
    private CiudadRepository ciudadRepository;

    @Override
    public List<Ciudad> listarCiudades() {
        return ciudadRepository.findAll();
    }

    @Override
    public List<Ciudad> listarCiudadesPorPais(Pais pais) {
        return ciudadRepository.findByPais(pais);
    }

    @Override
    public Ciudad guardarCiudad(Ciudad ciudad) {
        if (ciudadRepository.existsByNombreIgnoreCaseAndPais(ciudad.getNombre(), ciudad.getPais())) {
            throw new RuntimeException("La ciudad ya existe en este pais");
        }
        return ciudadRepository.save(ciudad);
    }

    @Override
    public Ciudad actualizarCiudad(Long id, Ciudad ciudad) {
        Ciudad existente = obtenerPorId(id);
        existente.setNombre(ciudad.getNombre());
        existente.setActivo(ciudad.isActivo());
        existente.setPais(ciudad.getPais());
        return ciudadRepository.save(existente);
    }

    @Override
    public void eliminarCiudad(Long id) {
        Ciudad ciudad = obtenerPorId(id);
        ciudadRepository.delete(ciudad);
    }

    @Override
    public Ciudad obtenerPorId(Long id) {
        Optional<Ciudad> opt = ciudadRepository.findById(id);
        if (opt.isEmpty()) {
            throw new RuntimeException("Ciudad no encontrada");
        }
        return opt.get();
    }
}