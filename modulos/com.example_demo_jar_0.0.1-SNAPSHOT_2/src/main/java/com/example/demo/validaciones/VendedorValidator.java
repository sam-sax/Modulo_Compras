package com.example.demo.validaciones;

import com.example.demo.dto.VendedorDTO;
import com.example.demo.repositorio.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class VendedorValidator implements Validator {

    @Autowired
    private VendedorRepository vendedorRepo;

    @Override
    public boolean supports(Class<?> clazz) {
        return VendedorDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        VendedorDTO dto = (VendedorDTO) target;

        if(dto.getCodigoVendedor() != null && vendedorRepo.existsByCodigoVendedor(dto.getCodigoVendedor())) {
            errors.rejectValue("codigoVendedor", "codigo.existente", "Código de vendedor ya existe");
        }

        if(dto.getPersonaId() == null) {
            errors.rejectValue("personaId", "persona.vacia", "Debe seleccionar una persona");
        }
    }
}
