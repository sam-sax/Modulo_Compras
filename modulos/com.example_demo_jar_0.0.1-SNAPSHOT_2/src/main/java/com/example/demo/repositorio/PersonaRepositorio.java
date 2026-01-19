package com.example.demo.repositorio;

import com.example.demo.modelo.Persona;
import com.example.demo.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

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
           "(:estadoCivil IS NULL OR p.estadoCivil = :estadoCivil) AND " +
           "(:rol IS NULL OR " +
           " (:rol = 'CLI' AND p.esCliente = true) OR " +
           " (:rol = 'PROV' AND p.esProveedor = true) OR " +
           " (:rol = 'VEND' AND p.esVendedor = true) OR " +
           " (:rol = 'COMP' AND p.esComprador = true) OR " +
           " (:rol = 'USER' AND (p.usuarioVinculado IS NOT NULL OR p.usuario IS NOT NULL)))")
    List<Persona> listarConFiltrosJPQL(@Param("q") String q, 
                                       @Param("rol") String rol,
                                       @Param("ciudadId") Long ciudadId, 
                                       @Param("estadoCivil") String estadoCivil);
    
    

            // personas sin cliente
        @Query("SELECT p FROM Persona p WHERE p.esCliente = false OR p.esCliente IS NULL")
        List<Persona> findPersonasDisponiblesParaCliente();
        
        //eliminar persona e insertar en log_personas
        @Modifying
    @Transactional
    @Query(value = "CALL sp_eliminarPersona(:id, :usuario)", nativeQuery = true)
    void eliminarPersonaConSP(@Param("id") Long id, @Param("usuario") String usuario);
    
    // En PersonaRepositorio.java
List<Persona> findByEsProveedorFalse();
    
}