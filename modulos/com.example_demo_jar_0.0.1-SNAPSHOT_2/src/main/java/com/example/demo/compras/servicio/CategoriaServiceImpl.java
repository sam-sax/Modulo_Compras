package com.example.demo.compras.servicio;

import com.example.demo.compras.Categoria;
import com.example.demo.compras.repositorio.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepo;

    @Override
    public List<Categoria> listarTodas() {
        return categoriaRepo.findAll();
    }

    @Override
    @Transactional
    public Categoria guardar(Categoria categoria) {
        return categoriaRepo.save(categoria);
    }

    @Override
    public Categoria buscarPorId(Long id) {
        return categoriaRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        categoriaRepo.deleteById(id);
    }
}