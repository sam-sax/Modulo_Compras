package com.example.demo.repositorio;

import com.example.demo.modelo.Persona;
import com.example.demo.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PersonaRepositorio extends JpaRepository<Persona, Long>, JpaSpecificationExecutor<Persona> {
    
    List<Persona> findByUsuario(Usuario usuario);
    Persona findByEmail(String email);
    Persona findByTelefono(String telefono);
    
    @Query("SELECT p FROM Persona p WHERE p.numeroCedula = :cedula")
    Persona findByNumeroCedula(@Param("cedula") String cedula);
    
    Persona findByRuc(String ruc);

    @Query("SELECT p FROM Persona p WHERE p.usuarioVinculado IS NULL AND p.usuario IS NULL AND p.esVendedor = false")
    List<Persona> findPersonasSinUsuario();

    // NUEVO: Método optimizado para el buscador del listado
    @Query("SELECT p FROM Persona p WHERE " +
           "(:q IS NULL OR LOWER(p.nombres) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           " LOWER(p.apellidos) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           " LOWER(p.razonSocial) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           " p.numeroCedula LIKE CONCAT('%', :q, '%') OR " +
           " p.ruc LIKE CONCAT('%', :q, '%')) AND " +
           "(:ciudadId IS NULL OR p.ciudad.id = :ciudadId) AND " +
           "(:estadoCivil IS NULL OR p.estadoCivil = :estadoCivil)")
    List<Persona> listarConFiltrosJPQL(@Param("q") String q, 
                                       @Param("ciudadId") Long ciudadId, 
                                       @Param("estadoCivil") String estadoCivil);
    
    
    

            // Agrega este método a tu PersonaRepositorio
        @Query("SELECT p FROM Persona p WHERE p.esCliente = false OR p.esCliente IS NULL")
        List<Persona> findPersonasDisponiblesParaCliente();
    
}