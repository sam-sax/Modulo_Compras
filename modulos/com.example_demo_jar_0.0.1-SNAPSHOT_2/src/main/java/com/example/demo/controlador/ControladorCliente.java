package com.example.demo.controlador;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.modelo.Persona;
import com.example.demo.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ControladorCliente {

    @Autowired 
    private ClienteServicio clienteServicio;

        @GetMapping("/listado")
    public String listar(Model model) {
        model.addAttribute("clientes", clienteServicio.listarTodos());
        return "ventas/clientes-listado"; // ❌ sin redirect
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        List<Persona> disponibles = clienteServicio.listarPersonasDisponibles();

        if (disponibles.isEmpty()) {
            model.addAttribute("error", "No existen personas disponibles.");
            model.addAttribute("clientes", clienteServicio.listarTodos());
            return "ventas/clientes-listado"; // ❌ sin redirect
        }

        model.addAttribute("clienteDTO", new ClienteDTO());
        model.addAttribute("personasDisponibles", disponibles);
        return "ventas/cliente-form"; // ❌ sin redirect
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("clienteDTO") ClienteDTO dto) {
        clienteServicio.guardarDesdeDTO(dto);
        return "redirect:/clientes/listado"; // ✅ Este sí puede ser redirect, porque queremos recargar la lista
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        ClienteDTO dto = clienteServicio.obtenerDTOporId(id);
        model.addAttribute("clienteDTO", dto);
        return "ventas/cliente-form"; // ❌ sin redirect
    }
}