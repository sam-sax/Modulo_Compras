package com.example.demo.compras.repositorio;

import com.example.demo.compras.NotaDebito;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface NotaDebitoSPRepository extends CrudRepository<NotaDebito, Long> {

    // Stored Procedure: sp_notas_debito_proveedor
    @Procedure(procedureName = "sp_notas_debito_proveedor")
    List<NotaDebito> obtenerNotasDebitoPorProveedor(Long proveedorId);
}
