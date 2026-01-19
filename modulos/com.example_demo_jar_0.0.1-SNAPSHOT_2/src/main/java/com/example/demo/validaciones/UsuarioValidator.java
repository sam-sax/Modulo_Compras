package com.example.demo.validaciones;

import com.example.demo.modelo.Usuario;
import com.example.demo.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UsuarioValidator implements Validator {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Override
    public boolean supports(Class<?> clazz) {
        return Usuario.class.equals(clazz);
    }

    @Override
  public void validate(Object target, Errors errors) {
    Usuario u = (Usuario) target;

    if (u.getUsuario() == null || u.getUsuario().trim().length() < 3) {
        errors.rejectValue("usuario", "usuario.corto", "Usuario muy corto (min 3)");
    }

    if (u.getClave() == null || u.getClave().length() < 4) {
        errors.rejectValue("clave", "clave.corta", "Clave muy corta (min 4)");
    }

    // Si existe ya en BD (cuando se registra nuevo)
    if (u.getUsuario() != null && usuarioServicio.buscarUsuarioPorNombreSP(u.getUsuario()).isPresent()) {
        errors.rejectValue("usuario", "usuario.exists", "El nombre de usuario ya existe");
    }
  }
}