package com.example.demo.compras.repositorio;

import com.example.demo.compras.CuentaPagar;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CuentaPagarSPRepository extends CrudRepository<CuentaPagar, Long> {

    @Procedure(procedureName = "sp_cuentas_vencidas")
    List<CuentaPagar> obtenerCuentasVencidas(String fechaActual);
}
