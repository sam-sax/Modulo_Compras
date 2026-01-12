package com.example.demo.servicios;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.modelo.Cliente;
import com.example.demo.modelo.Persona;
import java.util.List;

public interface ClienteServicio {
    List<Cliente> listarTodos();
    List<Persona> listarPersonasDisponibles(); // Para el combo de selección
    Cliente guardarDesdeDTO(ClienteDTO dto);
    ClienteDTO obtenerDTOporId(Long id);
    void eliminar(Long id);
}
