package com.example.demo.servicios;

import com.example.demo.modelo.Persona;
import com.example.demo.repositorio.PersonaRepositorio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PersonaServicioImplement implements PersonaServicio {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PersonaRepositorio personaRepositorio;

    @Override
    public List<Persona> listarConFiltros(String q, String rol, Long ciudadId, String estadoCivil) {
        final String queryTerm = (q != null && !q.trim().isEmpty()) ? q.trim() : null;
        final String estado = (estadoCivil != null && !estadoCivil.trim().isEmpty()) ? estadoCivil : null;

        return personaRepositorio.findAll((Specification<Persona>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (queryTerm != null) {
                String pattern = "%" + queryTerm.toLowerCase() + "%";
                predicates.add(cb.or(
                    cb.like(cb.lower(root.get("nombres")), pattern),
                    cb.like(cb.lower(root.get("apellidos")), pattern),
                    cb.like(cb.lower(root.get("razonSocial")), pattern),
                    cb.like(cb.lower(root.get("numeroCedula")), pattern),
                    cb.like(cb.lower(root.get("ruc")), pattern)
                ));
            }

            if (rol != null && !rol.isEmpty()) {
                switch (rol) {
                    case "PROV": predicates.add(cb.isTrue(root.get("esProveedor"))); break;
                    case "CLI":  predicates.add(cb.isTrue(root.get("esCliente"))); break;
                    case "VEND": predicates.add(cb.isTrue(root.get("esVendedor"))); break;
                    case "COMP": predicates.add(cb.isTrue(root.get("esComprador"))); break;
                    case "USER": predicates.add(cb.isNotNull(root.get("usuarioVinculado"))); break;
                }
            }

            if (ciudadId != null) {
                predicates.add(cb.equal(root.get("ciudad").get("id"), ciudadId));
            }

            if (estado != null) {
                predicates.add(cb.equal(root.get("estadoCivil"), estado));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

    @Override public List<Persona> listar() { return listarPersonaSP(); }

    @Override
    public Persona insertar(Persona p) {
        limpiarDatosSegunTipo(p);
        Long id = insertarPersonaSP(p.getNombres(), p.getApellidos(), p.getTelefono(), p.getDireccion(), p.getFechaNacimiento(), p.getUsuario() != null ? p.getUsuario().getId() : null, p.getUsuarioVinculado() != null ? p.getUsuarioVinculado().getIdUsuario() : null, p.getEmail(), p.getEstadoCivil(), p.getNumeroCedula(), p.getRuc(), p.getRazonSocial(), p.getRepresentanteLegal(), p.getTipoPersona() != null ? p.getTipoPersona().name() : "FISICA", p.getCiudad() != null ? p.getCiudad().getId() : null, p.getEsCliente(), p.getEsProveedor(), p.getEsVendedor(), p.getEsComprador());
        return buscarPersonaSP(id);
    }

    @Override
    public Persona actualizar(Persona p) {
        limpiarDatosSegunTipo(p);
        actualizarPersonaSP(p.getIdPersona(), p.getNombres(), p.getApellidos(), p.getTelefono(), p.getDireccion(), p.getFechaNacimiento(), p.getUsuario() != null ? p.getUsuario().getId() : null, p.getUsuarioVinculado() != null ? p.getUsuarioVinculado().getIdUsuario() : null, p.getEmail(), p.getEstadoCivil(), p.getNumeroCedula(), p.getRuc(), p.getRazonSocial(), p.getRepresentanteLegal(), p.getTipoPersona() != null ? p.getTipoPersona().name() : "FISICA", p.getCiudad() != null ? p.getCiudad().getId() : null, p.getEsCliente(), p.getEsProveedor(), p.getEsVendedor(), p.getEsComprador());
        return buscarPersonaSP(p.getIdPersona());
    }

    private void limpiarDatosSegunTipo(Persona p) {
        if ("JURIDICA".equals(p.getTipoPersona().name())) {
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

    @Override public void eliminar(Long id) { personaRepositorio.deleteById(id); }
    @Override public Persona buscarPorId(Long id) { return buscarPersonaSP(id); }
    @Override public Persona buscarPorEmail(String e) { return personaRepositorio.findByEmail(e); }
    @Override public Persona buscarPorTelefono(String t) { return personaRepositorio.findByTelefono(t); }
    @Override public Persona buscarPorNumeroCedula(String c) { return personaRepositorio.findByNumeroCedula(c); }
    @Override public Persona buscarPorRuc(String r) { return personaRepositorio.findByRuc(r); }
    @Override public List<Persona> buscarPersonasSinUsuario() { return personaRepositorio.findPersonasSinUsuario(); }

    @Override
    public Long insertarPersonaSP(String n, String a, String t, String d, LocalDate f, Long uId, Long uaId, String e, String ec, String nc, String ruc, String rs, String rl, String tp, Long cId, Boolean esc, Boolean esp, Boolean esv, Boolean escom) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_insertarPersona");
        registrarParametros(query);
        setParametros(query, n, a, t, d, f, uId, uaId, e, ec, nc, ruc, rs, rl, tp, cId, esc, esp, esv, escom);
        query.execute();
        Object res = query.getSingleResult();
        return res != null ? ((Number) res).longValue() : null;
    }

    @Override
    public void actualizarPersonaSP(Long id, String n, String a, String t, String d, LocalDate f, Long uId, Long uaId, String e, String ec, String nc, String ruc, String rs, String rl, String tp, Long cId, Boolean esc, Boolean esp, Boolean esv, Boolean escom) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_actualizarPersona");
        query.registerStoredProcedureParameter("p_id", Long.class, ParameterMode.IN);
        registrarParametros(query);
        query.setParameter("p_id", id);
        setParametros(query, n, a, t, d, f, uId, uaId, e, ec, nc, ruc, rs, rl, tp, cId, esc, esp, esv, escom);
        query.execute();
    }

    @Override public List<Persona> listarPersonaSP() { return entityManager.createStoredProcedureQuery("sp_listarPersona", Persona.class).getResultList(); }
    @Override public Persona buscarPersonaSP(Long id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_buscarPersonaPorId", Persona.class);
        query.registerStoredProcedureParameter("p_id", Long.class, ParameterMode.IN);
        query.setParameter("p_id", id);
        List<Persona> res = query.getResultList();
        return res.isEmpty() ? null : res.get(0);
    }

    private void registrarParametros(StoredProcedureQuery q) {
        q.registerStoredProcedureParameter("p_nombres", String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter("p_apellidos", String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter("p_telefono", String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter("p_direccion", String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter("p_fechaNacimiento", java.sql.Date.class, ParameterMode.IN);
        q.registerStoredProcedureParameter("p_usuarioId", Long.class, ParameterMode.IN);
        q.registerStoredProcedureParameter("p_usuarioAsociadoId", Long.class, ParameterMode.IN);
        q.registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter("p_estadoCivil", String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter("p_numeroCedula", String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter("p_ruc", String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter("p_razonSocial", String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter("p_representanteLegal", String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter("p_tipoPersona", String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter("p_ciudad_id", Long.class, ParameterMode.IN);
        q.registerStoredProcedureParameter("p_esCliente", Boolean.class, ParameterMode.IN);
        q.registerStoredProcedureParameter("p_esProveedor", Boolean.class, ParameterMode.IN);
        q.registerStoredProcedureParameter("p_esVendedor", Boolean.class, ParameterMode.IN);
        q.registerStoredProcedureParameter("p_esComprador", Boolean.class, ParameterMode.IN);
    }

    private void setParametros(StoredProcedureQuery q, String n, String a, String t, String d, LocalDate f, Long uId, Long uaId, String e, String ec, String nc, String ruc, String rs, String rl, String tp, Long cId, Boolean esc, Boolean esp, Boolean esv, Boolean escom) {
        q.setParameter("p_nombres", n); q.setParameter("p_apellidos", a); q.setParameter("p_telefono", t);
        q.setParameter("p_direccion", d); q.setParameter("p_fechaNacimiento", f != null ? java.sql.Date.valueOf(f) : null);
        q.setParameter("p_usuarioId", uId); q.setParameter("p_usuarioAsociadoId", uaId); q.setParameter("p_email", e);
        q.setParameter("p_estadoCivil", ec); q.setParameter("p_numeroCedula", nc); q.setParameter("p_ruc", ruc);
        q.setParameter("p_razonSocial", rs); q.setParameter("p_representanteLegal", rl); q.setParameter("p_tipoPersona", tp);
        q.setParameter("p_ciudad_id", cId); q.setParameter("p_esCliente", esc != null ? esc : false);
        q.setParameter("p_esProveedor", esp != null ? esp : false); q.setParameter("p_esVendedor", esv != null ? esv : false);
        q.setParameter("p_esComprador", escom != null ? escom : false);
    }
}