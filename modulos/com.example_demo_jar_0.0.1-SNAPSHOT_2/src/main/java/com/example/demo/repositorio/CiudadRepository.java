package com.example.demo.repositorio;

import com.example.demo.modelo.Ciudad;
import com.example.demo.modelo.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
    boolean existsByNombreIgnoreCaseAndPais(String nombre, Pais pais);
    List<Ciudad> findByPais(Pais pais);
}