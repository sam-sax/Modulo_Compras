package com.example.demo.repositorio;

import com.example.demo.modelo.Persona;
import com.example.demo.modelo.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepositorio extends JpaRepository<Persona, Long> {
    List<Persona> findByUsuario(Usuario usuario);
    
    // Métodos para validaciones y búsquedas
    Persona findByEmail(String email);
    Persona findByTelefono(String telefono);

    // NUEVOS: búsquedas por cédula y RUC
    Persona findByNumeroCedula(String numeroCedula);
    Persona findByRuc(String ruc);
    
    @Query("SELECT p FROM Persona p WHERE p.idPersona NOT IN " +
           "(SELECT u.persona.idPersona FROM Usuario u WHERE u.persona IS NOT NULL)")
    List<Persona> findPersonasSinUsuario();
}
