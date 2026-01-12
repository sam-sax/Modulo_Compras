package com.example.demo.compras.servicio;

import com.example.demo.compras.Ajuste;
import com.example.demo.compras.dto.AjusteDTO;
import com.example.demo.compras.repositorio.AjusteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AjusteServiceImpl implements AjusteService {

    @Autowired private AjusteRepository ajusteRepo;

    @Override
    public Ajuste registrarAjuste(AjusteDTO dto) {
        Ajuste ajuste = new Ajuste();
        ajuste.setFecha(dto.getFecha());
        ajuste.setMotivo(dto.getMotivo());
        ajuste.setSubtotal(dto.getSubtotal());
        ajuste.setIva(dto.getIva());
        ajuste.setTotal(dto.getTotal());
        return ajusteRepo.save(ajuste);
    }

    @Override
    public List<Ajuste> listarAjustes() {
        return ajusteRepo.findAll();
    }
}
