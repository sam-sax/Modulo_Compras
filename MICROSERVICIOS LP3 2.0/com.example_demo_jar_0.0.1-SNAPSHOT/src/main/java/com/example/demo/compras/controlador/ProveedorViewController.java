package com.example.demo.compras.controlador;

import com.example.demo.compras.repositorio.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/compras/proveedores")
public class ProveedorViewController {

    @Autowired 
    private ProveedorRepository proveedorRepo;

    @GetMapping("/vista")
    public String vistaProveedores(Model model) {
        model.addAttribute("proveedores", proveedorRepo.findAll());
        return "compras/proveedores"; // templates/compras/proveedores.html
    }
}
