package com.example.demo.servicios;

import com.example.demo.modelo.Persona;
import com.example.demo.modelo.TipoPersona;
import com.example.demo.repositorio.PersonaRepositorio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Transactional
@Service
public class PersonaServicioImplement implements PersonaServicio {

    @Autowired 
    private PersonaRepositorio repo;

    @PersistenceContext 
    private EntityManager em;

  @Transactional
@Override
public Persona guardar(Persona persona) {
    // Limpiar campos según tipo
    limpiarDatosSegunTipo(persona);

    // 🔑 Pasar usuario al trigger antes de guardar
    em.createNativeQuery("SET @usuario_actual = :usuario")
      .setParameter("usuario", persona.getUsuarioModificacion())
      .executeUpdate();

    // Guardar la persona (INSERT o UPDATE)
    Persona guardada = repo.save(persona);

    // Forzar sincronización inmediata para que el trigger se ejecute
    em.flush(); 
    
    return guardada;
}

    @Override
    public Persona buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Persona> listarConFiltros(String q, String rol, Long ciudadId, String estadoCivil) {
        return repo.listarConFiltrosJPQL(q, rol, ciudadId, estadoCivil);
    }

    @Override
    public Persona buscarPorEmail(String email) {
        return repo.findByEmail(email);
    }

    @Transactional
    @Override
    public void eliminar(Long id, String usuario) {
    // 1. Seteamos la variable para el trigger
    em.createNativeQuery("SET @usuario_actual = :usuario")
      .setParameter("usuario", usuario)
      .executeUpdate();

    // 2. Llamamos al Stored Procedure que hace la eliminación lógica
    repo.eliminarPersonaConSP(id, usuario);

    // 3. Forzamos flush para disparar triggers
    em.flush();
}


    @Override
    public List<Persona> listar() {
        return repo.findAll();
    }

    @Override
    public Persona buscarPorTelefono(String telefono) {
        return repo.findByTelefono(telefono);
    }

    @Override
    public Persona buscarPorNumeroCedula(String numeroCedula) {
        return repo.findByNumeroCedula(numeroCedula);
    }

    @Override
    public Persona buscarPorRuc(String ruc) {
        return repo.findByRuc(ruc);
    }

    @Override
    public List<Persona> buscarPersonasSinUsuario() {
        return repo.findPersonasSinUsuario();
    }

    private void limpiarDatosSegunTipo(Persona p) {
        if (p.getTipoPersona() == TipoPersona.JURIDICA) {
            p.setEstadoCivil(null);
            p.setNombres(null);
            p.setApellidos(null);
            p.setNumeroCedula(null);
            p.setFechaNacimiento(null);
        } else {
            p.setRazonSocial(null);
            p.setRepresentanteLegal(null);
            p.setRuc(null);
        }
    }
}