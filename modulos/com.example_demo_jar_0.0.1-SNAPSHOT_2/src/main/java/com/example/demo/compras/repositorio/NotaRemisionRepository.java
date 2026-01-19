package com.example.demo.compras.repositorio;

import com.example.demo.compras.NotaRemision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotaRemisionRepository extends JpaRepository<NotaRemision, Long> {
    
    // Spring Data JPA generará automáticamente la consulta SQL: 
    // SELECT * FROM notas_remision WHERE numero = ?
    NotaRemision findByNumero(String numero);
}