package com.example.demo.compras.controlador;

import com.example.demo.compras.Proveedor;
import com.example.demo.compras.repositorio.ProveedorRepository;
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
@RequestMapping("/compras/proveedores")
public class ProveedorRestController {

    @Autowired private ProveedorRepository proveedorRepo;

    @GetMapping
    public List<Proveedor> listarTodos() {
        return proveedorRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<?> registrarProveedor(@Valid @RequestBody Proveedor proveedor, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        return ResponseEntity.ok(proveedorRepo.save(proveedor));
    }
}

