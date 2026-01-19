package com.example.demo.compras.controlador;

import com.example.demo.compras.dto.NotaCreditoDTO;
import com.example.demo.compras.servicio.NotaCreditoService;
import com.example.demo.compras.servicio.ProveedorService;
import com.example.demo.compras.servicio.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/compras/notas-credito")
public class NotaCreditoViewController {

    @Autowired private NotaCreditoService notaService;
    @Autowired private ProveedorService proveedorService;
    @Autowired private ProductoService productoService;

    @GetMapping("/vista")
    public String vistaNotas(@RequestParam(name = "proveedorId", required = false) Long proveedorId, Model model) {
        model.addAttribute("proveedores", proveedorService.listarProveedores());
        
        // Si hay proveedorId, podrías usar el SP (necesitas agregar el método al Service)
        // Por defecto listamos todas
        model.addAttribute("notasCredito", notaService.listarNotas());
        return "compras/notasCredito";
    }

    @GetMapping("/nuevo")
    public String nuevaNotaForm(Model model) {
        model.addAttribute("notaCreditoDTO", new NotaCreditoDTO());
        model.addAttribute("proveedores", proveedorService.listarProveedores());
        model.addAttribute("productos", productoService.listarProductos());
        return "compras/notasCredito-form";
    }

    @PostMapping("/guardar")
    public String guardarNota(@ModelAttribute NotaCreditoDTO dto, RedirectAttributes ra) {
        try {
            notaService.registrarNota(dto);
            ra.addFlashAttribute("mensaje", "Nota de Crédito registrada y stock actualizado.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/compras/notas-credito/vista";
    }
}