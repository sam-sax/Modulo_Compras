package com.example.demo.controlador;

import com.example.demo.modelo.Pais;
import com.example.demo.servicios.PaisService;
import com.example.demo.validaciones.PaisValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paises")
public class PaisController {

    @Autowired
    private PaisService paisService;

    @Autowired
    private PaisValidator paisValidator;

    @GetMapping
    public List<Pais> listarPaises() {
        return paisService.listarPaises();
    }

    @GetMapping("/{id}")
    public Pais obtenerPais(@PathVariable Long id) {
        return paisService.obtenerPorId(id);
    }

    @PostMapping
    public Object crearPais(@RequestBody Pais pais) {
        BindingResult result = new BeanPropertyBindingResult(pais, "pais");
        paisValidator.validate(pais, result);
        if (result.hasErrors()) {
            return result.getAllErrors();
        }
        return paisService.guardarPais(pais);
    }

    @PutMapping("/{id}")
    public Object actualizarPais(@PathVariable Long id, @RequestBody Pais pais) {
        BindingResult result = new BeanPropertyBindingResult(pais, "pais");
        paisValidator.validate(pais, result);
        if (result.hasErrors()) {
            return result.getAllErrors();
        }
        return paisService.actualizarPais(id, pais);
    }

    @DeleteMapping("/{id}")
    public void eliminarPais(@PathVariable Long id) {
        paisService.eliminarPais(id);
    }
}
