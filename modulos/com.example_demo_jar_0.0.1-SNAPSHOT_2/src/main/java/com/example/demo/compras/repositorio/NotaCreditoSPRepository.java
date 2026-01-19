package com.example.demo.compras.repositorio;

import com.example.demo.compras.NotaCredito;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface NotaCreditoSPRepository extends CrudRepository<NotaCredito, Long> {

    @Procedure(procedureName = "sp_notas_credito_proveedor")
    List<NotaCredito> obtenerNotasCreditoPorProveedor(Long proveedorId);
}
