package com.example.demo.compras.controlador;

import com.example.demo.compras.dto.ProductoDTO;
import com.example.demo.compras.repositorio.CategoriaRepository;
import com.example.demo.compras.servicio.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/compras/productos")
public class ProductoViewController {

    @Autowired 
    private ProductoService productoService;
    
     @Autowired
    private CategoriaRepository categoriaRepo;

    // Listado de todos los productos
    @GetMapping("/vista")
    public String vistaProductos(Model model) {
        model.addAttribute("productos", productoService.listarProductos());
        return "compras/productos";
    }

    // Formulario para crear un nuevo producto
    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("productoDTO", new ProductoDTO());
        model.addAttribute("categorias", categoriaRepo.findAll());
        return "compras/productos-form";
    }

            // Procesar el guardado del producto
        @PostMapping("/guardar")
public String guardarProducto(@ModelAttribute ProductoDTO productoDTO, RedirectAttributes ra) {
    try {
        productoService.registrarProducto(productoDTO);
        ra.addFlashAttribute("mensaje", "Producto '" + productoDTO.getNombre() + "' registrado con éxito.");
    } catch (Exception e) {
        ra.addFlashAttribute("error", "Error al registrar el producto: " + e.getMessage());
    }
    // CAMBIO: Debe apuntar a la ruta completa del GetMapping de la lista
    return "redirect:/compras/productos/vista"; 
}
}