package com.example.demo.compras.controlador;

import com.example.demo.compras.repositorio.PresupuestoProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/compras/presupuestos")
public class PresupuestoProveedorViewController {

    @Autowired
    private PresupuestoProveedorRepository presupuestoRepo;

    @GetMapping("/vista")
    public String vistaPresupuestos(Model model) {
        model.addAttribute("presupuestos", presupuestoRepo.findAll());
        return "compras/presupuestos"; // templates/compras/presupuestos.html
    }
}
