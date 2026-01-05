package com.example.demo.servicios;

import com.example.demo.modelo.Persona;
import com.example.demo.repositorio.PersonaRepositorio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonaServicioImplement implements PersonaServicio {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PersonaRepositorio personaRepositorio;

    // =================================================================================
    // MÉTODOS CRUD "CLÁSICOS"
    // =================================================================================

    @Override
    public List<Persona> listar() {
        return listarPersonaSP();
    }

    @Override
    public Persona insertar(Persona persona) {
        Long idGenerado = insertarPersonaSP(
            persona.getNombres(),
            persona.getApellidos(),
            persona.getTelefono(),
            persona.getDireccion(),
            persona.getFechaNacimiento(),
            persona.getUsuario() != null ? persona.getUsuario().getId() : null,
            persona.getUsuarioVinculado() != null ? persona.getUsuarioVinculado().getIdUsuario() : null,
            persona.getEmail(),
            persona.getEstadoCivil(),
            persona.getCiudad(),
            persona.getPais(),
            persona.getNumeroCedula(),
            persona.getRuc()
        );
        return buscarPersonaSP(idGenerado);
    }

    @Override
    public Persona actualizar(Persona persona) {
        actualizarPersonaSP(
            persona.getIdPersona(),
            persona.getNombres(),
            persona.getApellidos(),
            persona.getTelefono(),
            persona.getDireccion(),
            persona.getFechaNacimiento(),
            persona.getUsuario() != null ? persona.getUsuario().getId() : null,
            persona.getUsuarioVinculado() != null ? persona.getUsuarioVinculado().getIdUsuario() : null,
            persona.getEmail(),
            persona.getEstadoCivil(),
            persona.getCiudad(),
            persona.getPais(),
            persona.getNumeroCedula(),
            persona.getRuc()
        );
        return buscarPersonaSP(persona.getIdPersona());
    }

    @Override
    public void eliminar(Long id) {
        eliminarPersonaSP(id, "usuario_actual");
    }

    @Override
    public Persona buscarPorId(Long id) {
        return buscarPersonaSP(id);
    }

    @Override
    public Optional<Persona> getUsuarioByNombre(String persona) {
        return buscarPersonaPorNombreSP(persona);
    }

    // =================================================================================
    // MÉTODOS QUE USAN PROCEDIMIENTOS ALMACENADOS (SPs)
    // =================================================================================

    @Override
    public Long insertarPersonaSP(String nombres, String apellidos, String telefono, String direccion,
                                  LocalDate fechaNacimiento, Long usuarioId,
                                  Long usuarioAsociadoId, String email, String estadoCivil,
                                  String ciudad, String pais, String numeroCedula, String ruc) {

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_insertarPersona");

        query.registerStoredProcedureParameter("p_nombres", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_apellidos", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_telefono", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_direccion", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_fechaNacimiento", java.sql.Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_usuarioId", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_usuarioAsociadoId", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_estadoCivil", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_ciudad", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_pais", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_numeroCedula", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_ruc", String.class, ParameterMode.IN);

        query.setParameter("p_nombres", nombres);
        query.setParameter("p_apellidos", apellidos);
        query.setParameter("p_telefono", telefono);
        query.setParameter("p_direccion", direccion);
        query.setParameter("p_fechaNacimiento", fechaNacimiento != null ? java.sql.Date.valueOf(fechaNacimiento) : null);
        query.setParameter("p_usuarioId", usuarioId != null ? usuarioId.intValue() : null);
        query.setParameter("p_usuarioAsociadoId", usuarioAsociadoId != null ? usuarioAsociadoId.intValue() : null);
        query.setParameter("p_email", email);
        query.setParameter("p_estadoCivil", estadoCivil);
        query.setParameter("p_ciudad", ciudad);
        query.setParameter("p_pais", pais);
        query.setParameter("p_numeroCedula", numeroCedula);
        query.setParameter("p_ruc", ruc);

        query.execute();
        // Dependiendo del SP puede devolver last_insert_id; aquí asumimos que el SP retorna el id (como NUMBER)
        Object single = query.getSingleResult();
        return single != null ? ((Number) single).longValue() : null;
    }

    @Override
    public Long actualizarPersonaSP(Long id, String nombres, String apellidos, String telefono, 
                                   String direccion, LocalDate fechaNacimiento, Long usuarioId,
                                   Long usuarioAsociadoId, String email, String estadoCivil,
                                   String ciudad, String pais, String numeroCedula, String ruc) {

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_actualizarPersona");

        query.registerStoredProcedureParameter("p_id", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_nombres", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_apellidos", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_telefono", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_direccion", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_fechaNacimiento", java.sql.Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_usuarioId", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_usuarioAsociadoId", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_estadoCivil", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_ciudad", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_pais", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_numeroCedula", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_ruc", String.class, ParameterMode.IN);

        query.setParameter("p_id", id.intValue());
        query.setParameter("p_nombres", nombres);
        query.setParameter("p_apellidos", apellidos);
        query.setParameter("p_telefono", telefono);
        query.setParameter("p_direccion", direccion);
        query.setParameter("p_fechaNacimiento", fechaNacimiento != null ? java.sql.Date.valueOf(fechaNacimiento) : null);
        query.setParameter("p_usuarioId", usuarioId != null ? usuarioId.intValue() : null);
        query.setParameter("p_usuarioAsociadoId", usuarioAsociadoId != null ? usuarioAsociadoId.intValue() : null);
        query.setParameter("p_email", email);
        query.setParameter("p_estadoCivil", estadoCivil);
        query.setParameter("p_ciudad", ciudad);
        query.setParameter("p_pais", pais);
        query.setParameter("p_numeroCedula", numeroCedula);
        query.setParameter("p_ruc", ruc);

        query.execute();

        return id;
    }

    @Override
    public Boolean eliminarPersonaSP(Long id, String usuarioEliminador) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_eliminarPersona");
        
        query.registerStoredProcedureParameter("p_id", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_usuario", String.class, ParameterMode.IN);

        query.setParameter("p_id", id.intValue());
        query.setParameter("p_usuario", usuarioEliminador);

        query.execute();
        return true;
    }

    @Override
    public Persona buscarPersonaSP(Long id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_buscarPersonaPorId", Persona.class);
        query.registerStoredProcedureParameter("p_id", Integer.class, ParameterMode.IN);
        query.setParameter("p_id", id.intValue());
        
        List<Persona> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    @Override
    public List<Persona> listarPersonaSP() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_listarPersona", Persona.class);
        return query.getResultList();
    }

    @Override
    public Optional<Persona> buscarPersonaPorNombreSP(String nombre) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_buscarPersonaPorNombre", Persona.class);
        query.registerStoredProcedureParameter("p_nombres", String.class, ParameterMode.IN);
        query.setParameter("p_nombres", nombre);
        
        List<Persona> resultados = query.getResultList();
        return resultados.isEmpty() ? Optional.empty() : Optional.of(resultados.get(0));
    }
    
    // =================================================================================
    // NUEVOS MÉTODOS PARA VALIDACIONES (consulta directa)
    // =================================================================================
    
    @Override
    public Persona buscarPorEmail(String email) {
        try {
            return personaRepositorio.findByEmail(email);
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public Persona buscarPorTelefono(String telefono) {
        try {
            return personaRepositorio.findByTelefono(telefono);
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<Persona> buscarPersonasSinUsuario() {
        return personaRepositorio.findPersonasSinUsuario();
    }

    @Override
    public Persona buscarPorNumeroCedula(String numeroCedula) {
        try {
            return personaRepositorio.findByNumeroCedula(numeroCedula);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Persona buscarPorRuc(String ruc) {
        try {
            return personaRepositorio.findByRuc(ruc);
        } catch (Exception e) {
            return null;
        }
    }
}
