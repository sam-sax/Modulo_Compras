package com.example.demo.servicios;

import com.example.demo.modelo.Persona;
import java.util.List;

public interface PersonaServicio {

    // CRUD
    Persona guardar(Persona persona);

    /**
     * Eliminación lógica usando Stored Procedure
     * @param id id de la persona
     * @param usuario usuario autenticado del sistema
     */
    void eliminar(Long id, String usuario);

    Persona buscarPorId(Long id);
    List<Persona> listar();

    // BÚSQUEDAS
    Persona buscarPorEmail(String email);
    Persona buscarPorTelefono(String telefono);
    Persona buscarPorNumeroCedula(String numeroCedula);
    Persona buscarPorRuc(String ruc);

    List<Persona> buscarPersonasSinUsuario();

    // FILTROS
    List<Persona> listarConFiltros(String q, String rol, Long ciudadId, String estadoCivil);
}
