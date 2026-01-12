package com.example.demo.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControladorLogin {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/invitado")
    public String invitado(Model model) {
        model.addAttribute("mensaje", "Bienvenido, invitado!");
        return "bienvenido";
    }
}
