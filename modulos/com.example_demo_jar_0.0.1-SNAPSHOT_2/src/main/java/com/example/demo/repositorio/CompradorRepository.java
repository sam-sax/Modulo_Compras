package com.example.demo.repositorio;

import com.example.demo.modelo.Comprador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface CompradorRepository extends JpaRepository<Comprador, Long> {

                    @Query("""
                    SELECT c 
                    FROM Comprador c 
                    JOIN FETCH c.persona
                    JOIN FETCH c.usuario
                    WHERE c.activo = true
                """)
    List<Comprador> listarActivos();

    Optional<Comprador> findByUsuarioUsuario(String usuario);

    boolean existsByCodigoComprador(String codigoComprador);
}