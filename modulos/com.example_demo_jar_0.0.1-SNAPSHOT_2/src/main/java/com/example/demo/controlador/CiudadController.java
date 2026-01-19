package com.example.demo.controlador;

import com.example.demo.modelo.Ciudad;
import com.example.demo.modelo.Pais;
import com.example.demo.servicios.CiudadService;
import com.example.demo.servicios.PaisService;
import com.example.demo.validaciones.CiudadValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ciudades")
public class CiudadController {

    @Autowired
    private CiudadService ciudadService;

    @Autowired
    private PaisService paisService;

    @Autowired
    private CiudadValidator ciudadValidator;

    @GetMapping
    public List<Ciudad> listarCiudades() {
        return ciudadService.listarCiudades();
    }

    @GetMapping("/por-pais/{paisId}")
    public List<Ciudad> listarPorPais(@PathVariable Long paisId) {
        Pais pais = paisService.obtenerPorId(paisId);
        return ciudadService.listarCiudadesPorPais(pais);
    }

    @GetMapping("/{id}")
    public Ciudad obtenerCiudad(@PathVariable Long id) {
        return ciudadService.obtenerPorId(id);
    }

    @PostMapping
    public Object crearCiudad(@RequestParam Long paisId, @RequestBody Ciudad ciudad) {
        Pais pais = paisService.obtenerPorId(paisId);
        ciudad.setPais(pais);

        BindingResult result = new BeanPropertyBindingResult(ciudad, "ciudad");
        ciudadValidator.validate(ciudad, result);
        if (result.hasErrors()) {
            return result.getAllErrors();
        }
        return ciudadService.guardarCiudad(ciudad);
    }

    @PutMapping("/{id}")
    public Object actualizarCiudad(@PathVariable Long id, @RequestParam Long paisId, @RequestBody Ciudad ciudad) {
        Pais pais = paisService.obtenerPorId(paisId);
        ciudad.setPais(pais);

        BindingResult result = new BeanPropertyBindingResult(ciudad, "ciudad");
        ciudadValidator.validate(ciudad, result);
        if (result.hasErrors()) {
            return result.getAllErrors();
        }
        return ciudadService.actualizarCiudad(id, ciudad);
    }

    @DeleteMapping("/{id}")
    public void eliminarCiudad(@PathVariable Long id) {
        ciudadService.eliminarCiudad(id);
    }
}
