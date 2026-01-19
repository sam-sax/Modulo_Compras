package com.example.demo.compras.controlador;

import com.example.demo.compras.dto.NotaRemisionDTO;
import com.example.demo.compras.servicio.NotaRemisionService;
import com.example.demo.compras.servicio.ProveedorService;
import com.example.demo.compras.servicio.ProductoService;

import com.example.demo.servicios.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/compras/remisiones")
public class NotaRemisionViewController {

    @Autowired private NotaRemisionService remisionService;
    @Autowired private ProveedorService proveedorService;
    @Autowired private ProductoService productoService;
    @Autowired private VendedorService vendedorService;

    @GetMapping("/vista")
    public String vistaRemisiones(@RequestParam(name = "numero", required = false) String numero, Model model) {
        if (numero != null && !numero.isEmpty()) {
            model.addAttribute("remisiones", remisionService.buscarPorNumero(numero));
        } else {
            model.addAttribute("remisiones", remisionService.listarRemisiones());
        }
        return "compras/remisiones";
    }

    @GetMapping("/nuevo")
    public String nuevaRemisionForm(Model model) {
        model.addAttribute("notaRemisionDTO", new NotaRemisionDTO());
        model.addAttribute("proveedores", proveedorService.listarProveedores());
        model.addAttribute("productos", productoService.listarProductos());
        model.addAttribute("vendedores", vendedorService.listarActivos());
        return "compras/remisiones-form";
    }

    @PostMapping("/guardar")
    public String guardarRemision(@ModelAttribute NotaRemisionDTO dto, RedirectAttributes ra) {
        try {
            remisionService.registrarRemision(dto);
            ra.addFlashAttribute("mensaje", "Nota de Remisión registrada. Stock actualizado correctamente.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al procesar la remisión: " + e.getMessage());
        }
        return "redirect:/compras/remisiones/vista";
    }
}