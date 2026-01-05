package com.example.demo.compras.controlador;

import com.example.demo.compras.OrdenCompra;
import com.example.demo.compras.dto.OrdenCompraDTO;
import com.example.demo.compras.servicio.OrdenCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

// REST Controller → JSON
@RestController
@RequestMapping("/compras/ordenes")
public class OrdenCompraRestController {

    @Autowired
    private OrdenCompraService ordenService;

    @PostMapping
    public ResponseEntity<?> registrarOrden(@Valid @RequestBody OrdenCompraDTO dto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        return ResponseEntity.ok(ordenService.registrarOrden(dto));
    }

    @GetMapping
    public ResponseEntity<List<OrdenCompra>> listarOrdenes() {
        return ResponseEntity.ok(ordenService.listarOrdenes());
    }
}

