package com.example.demo.servicios;



import com.example.demo.modelo.Pais;
import com.example.demo.repositorio.PaisRepository;
import com.example.demo.servicios.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaisServiceImpl implements PaisService {

    @Autowired
    private PaisRepository paisRepository;

    @Override
    public List<Pais> listarPaises() {
        return paisRepository.findAll();
    }

    @Override
    public Pais guardarPais(Pais pais) {
        if (paisRepository.existsByNombreIgnoreCase(pais.getNombre())) {
            throw new RuntimeException("El pais ya existe");
        }
        return paisRepository.save(pais);
    }

    @Override
    public Pais actualizarPais(Long id, Pais pais) {
        Pais existente = obtenerPorId(id);
        existente.setNombre(pais.getNombre());
        existente.setActivo(pais.isActivo());
        return paisRepository.save(existente);
    }

    @Override
    public void eliminarPais(Long id) {
        Pais pais = obtenerPorId(id);
        paisRepository.delete(pais);
    }

    @Override
    public Pais obtenerPorId(Long id) {
        Optional<Pais> opt = paisRepository.findById(id);
        if (opt.isEmpty()) {
            throw new RuntimeException("Pais no encontrado");
        }
        return opt.get();
    }
}
