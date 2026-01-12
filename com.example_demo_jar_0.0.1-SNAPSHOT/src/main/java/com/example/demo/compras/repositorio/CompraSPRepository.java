package com.example.demo.compras.repositorio;

import com.example.demo.compras.Compra;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;

public interface CompraSPRepository extends CrudRepository<Compra, Long> {

    @Procedure(procedureName = "sp_cierre_compras")
    void ejecutarCierreCompras(String fechaCierre);
}
