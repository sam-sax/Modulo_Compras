package com.example.demo.compras.repositorio;

import com.example.demo.compras.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {

    @Query("SELECT o FROM OrdenCompra o WHERE o.estado = 'CONFIRMADA' AND o.fecha BETWEEN :inicio AND :fin")
    List<OrdenCompra> findOrdenesConfirmadasEntreFechas(LocalDate inicio, LocalDate fin);
}
