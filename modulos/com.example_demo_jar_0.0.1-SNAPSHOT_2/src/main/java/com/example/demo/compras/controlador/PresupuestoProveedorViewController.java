package com.example.demo.compras.controlador;

import com.example.demo.compras.PresupuestoProveedor;
import com.example.demo.compras.dto.OrdenCompraDTO;
import com.example.demo.compras.dto.PresupuestoProveedorDTO;
import com.example.demo.compras.servicio.PresupuestoProveedorService;
import com.example.demo.compras.servicio.ProductoService;
import com.example.demo.compras.servicio.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/compras/presupuestos")
public class PresupuestoProveedorViewController {

    @Autowired private PresupuestoProveedorService presupuestoService;
    @Autowired private ProveedorService proveedorService;
    @Autowired private ProductoService productoService;

    // Listado de todos los presupuestos
    @GetMapping("/vista")
    public String vistaPresupuestos(Model model) {
        model.addAttribute("presupuestos", presupuestoService.listarTodos());
        return "compras/presupuestos";
    }

    // Formulario para carga de nuevo presupuesto con tabla dinámica
    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        PresupuestoProveedorDTO dto = new PresupuestoProveedorDTO();
        dto.setItems(new java.util.ArrayList<>());
        
        model.addAttribute("presupuestoDTO", dto);
        model.addAttribute("proveedores", proveedorService.listarProveedores());
        model.addAttribute("productos", productoService.listarProductos());
        return "compras/presupuestos-form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute PresupuestoProveedorDTO dto, RedirectAttributes ra) {
        try {
            presupuestoService.guardar(dto);
            ra.addFlashAttribute("mensaje", "Presupuesto guardado exitosamente");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
        }
        return "redirect:/compras/presupuestos/vista";
    }

    @GetMapping("/convertir/{id}")
    public String convertirAOrden(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            PresupuestoProveedor presupuesto = presupuestoService.buscarPorId(id);
            
            OrdenCompraDTO ordenDTO = new OrdenCompraDTO();
            ordenDTO.setProveedorId(presupuesto.getProveedor().getId());
            ordenDTO.setFecha(LocalDate.now());
            
            model.addAttribute("ordenCompraDTO", ordenDTO);
            model.addAttribute("proveedores", proveedorService.listarProveedores());
            model.addAttribute("productos", productoService.listarProductos());
            
            return "compras/ordenes-form";
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al convertir: " + e.getMessage());
            return "redirect:/compras/presupuestos/vista";
        }
    }
}