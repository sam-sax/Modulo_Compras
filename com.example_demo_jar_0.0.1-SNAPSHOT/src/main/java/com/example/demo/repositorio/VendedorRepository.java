package com.example.demo.repositorio;

import com.example.demo.modelo.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {

    @Query("SELECT v FROM Vendedor v JOIN FETCH v.persona WHERE v.activo = true")
    List<Vendedor> listarActivos();

    Optional<Vendedor> findByUsuarioUsuario(String usuario);

    boolean existsByCodigoVendedor(String codigoVendedor);
}
