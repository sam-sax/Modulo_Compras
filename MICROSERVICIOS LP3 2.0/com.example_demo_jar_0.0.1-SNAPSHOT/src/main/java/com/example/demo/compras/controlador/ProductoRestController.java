package com.example.demo.compras.controlador;

import com.example.demo.compras.Producto;
import com.example.demo.compras.repositorio.ProductoRepository;
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
@RequestMapping("/compras/productos")
public class ProductoRestController {

    @Autowired private ProductoRepository productoRepo;

    @GetMapping
    public List<Producto> listarTodos() {
        return productoRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<?> registrarProducto(@Valid @RequestBody Producto producto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        return ResponseEntity.ok(productoRepo.save(producto));
    }
}

