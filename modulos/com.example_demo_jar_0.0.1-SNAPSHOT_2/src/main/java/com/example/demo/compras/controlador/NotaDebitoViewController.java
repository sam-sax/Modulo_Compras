package com.example.demo.compras.controlador;

import com.example.demo.compras.dto.NotaDebitoDTO;
import com.example.demo.compras.servicio.NotaDebitoService;
import com.example.demo.compras.servicio.ProveedorService;
import com.example.demo.compras.servicio.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/compras/notas-debito")
public class NotaDebitoViewController {

    @Autowired private NotaDebitoService notaService;
    @Autowired private ProveedorService proveedorService;
    @Autowired private ProductoService productoService;

    @GetMapping("/vista")
    public String vistaNotas(@RequestParam(name = "proveedorId", required = false) Long proveedorId, Model model) {
        model.addAttribute("proveedores", proveedorService.listarProveedores());
        
        if (proveedorId != null) {
            model.addAttribute("notasDebito", notaService.listarPorProveedor(proveedorId));
        } else {
            model.addAttribute("notasDebito", notaService.listarNotas());
        }
        return "compras/notasDebito";
    }

    @GetMapping("/nuevo")
    public String nuevaNotaForm(Model model) {
        model.addAttribute("notaDebitoDTO", new NotaDebitoDTO());
        model.addAttribute("proveedores", proveedorService.listarProveedores());
        model.addAttribute("productos", productoService.listarProductos());
        return "compras/notasDebito-form";
    }

    @PostMapping("/guardar")
    public String guardarNota(@ModelAttribute NotaDebitoDTO dto, RedirectAttributes ra) {
        try {
            notaService.registrarNota(dto);
            ra.addFlashAttribute("mensaje", "Nota de Débito registrada. El stock ha sido incrementado.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al registrar: " + e.getMessage());
        }
        return "redirect:/compras/notas-debito/vista";
    }
}