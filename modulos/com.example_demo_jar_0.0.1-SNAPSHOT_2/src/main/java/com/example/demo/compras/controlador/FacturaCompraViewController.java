package com.example.demo.compras.controlador;

import com.example.demo.compras.repositorio.FacturaCompraRepository;
import com.example.demo.compras.repositorio.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/compras/facturas")
public class FacturaCompraViewController {

    @Autowired private FacturaCompraRepository facturaRepo;
    @Autowired private ProveedorRepository proveedorRepo;

    @GetMapping("/nuevo")
    public String nuevaFactura(Model model) {
        model.addAttribute("proveedores", proveedorRepo.findAll());
        return "compras/factura-form";
    }

    @GetMapping("/vista")
    public String listadoFacturas(Model model) {
        model.addAttribute("facturas", facturaRepo.findAll());
        return "compras/factura-listado";
    }
}