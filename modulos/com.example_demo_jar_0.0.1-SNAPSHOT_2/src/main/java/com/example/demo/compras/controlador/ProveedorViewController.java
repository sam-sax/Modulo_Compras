package com.example.demo.compras.controlador;

import com.example.demo.compras.dto.ProveedorDTO;
import com.example.demo.compras.repositorio.ProveedorRepository;
import com.example.demo.compras.servicio.ProveedorService;
import com.example.demo.repositorio.PersonaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/compras/proveedores")
public class ProveedorViewController {

    @Autowired 
    private ProveedorService proveedorService;

    @Autowired 
    private PersonaRepositorio personaRepo;

    @GetMapping("/vista")
    public String vistaProveedores(Model model) {
        model.addAttribute("proveedores", proveedorService.listarProveedores());
        return "compras/proveedores";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("proveedorDTO", new ProveedorDTO());
        // Listamos personas que NO sean proveedores aún para evitar duplicados
        model.addAttribute("personas", personaRepo.findByEsProveedorFalse());
        return "compras/proveedores-form";
    }

    @PostMapping("/guardar")
    public String guardarProveedor(@ModelAttribute ProveedorDTO proveedorDTO, RedirectAttributes ra) {
        try {
            proveedorService.registrarProveedor(proveedorDTO);
            ra.addFlashAttribute("mensaje", "Proveedor registrado con éxito.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al registrar: " + e.getMessage());
        }
        return "redirect:/compras/proveedores/vista";
    }
}