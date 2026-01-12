package com.example.demo.repositorio;

import com.example.demo.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
    // Puedes agregar búsquedas por la persona relacionada
    Cliente findByPersonaIdPersona(Long idPersona);
}