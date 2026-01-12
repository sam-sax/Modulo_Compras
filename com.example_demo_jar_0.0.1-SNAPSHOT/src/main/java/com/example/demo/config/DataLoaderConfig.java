package com.example.demo.config;

import com.example.demo.modelo.Pais;
import com.example.demo.modelo.Ciudad;
import com.example.demo.repositorio.PaisRepository;
import com.example.demo.repositorio.CiudadRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class DataLoaderConfig {

    @Bean
    CommandLineRunner precargarPaisesCiudades(PaisRepository paisRepo, CiudadRepository ciudadRepo) {
        return args -> {
            if (paisRepo.count() > 0) {
                System.out.println(" Datos geograficos ya existentes.");
                return;
            }

            System.out.println("⏳ Cargando JSON global...");
            
            ObjectMapper mapper = new ObjectMapper();
            // ASEGÚRATE que el archivo esté en src/main/resources/data/countries+cities.json
            InputStream stream = getClass().getResourceAsStream("/data/countries+cities.json");

            if (stream == null) {
                System.err.println(" ARCHIVO NO ENCONTRADO");
                return;
            }

            try {
                List<Map<String, Object>> paisesData = mapper.readValue(stream, new TypeReference<>() {});
                System.out.println("? Total de países detectados: " + paisesData.size());

                for (Map<String, Object> paisMap : paisesData) {
                    String nombrePais = (String) paisMap.get("name");
                    
                    Pais pais = new Pais();
                    pais.setNombre(nombrePais);
                    pais = paisRepo.save(pais);

                    List<String> ciudadesNombres = (List<String>) paisMap.get("cities");
                    if (ciudadesNombres != null) {
                        List<Ciudad> batch = new ArrayList<>();
                        for (String nombreC : ciudadesNombres) {
                            Ciudad c = new Ciudad();
                            c.setNombre(nombreC);
                            c.setPais(pais);
                            batch.add(c);
                        }
                        ciudadRepo.saveAll(batch);
                    }
                    // Log para ver el progreso en consola
                    System.out.print(" \r🌍 Cargado: " + nombrePais);
                }
                System.out.println("\nCARGA COMPLETA EXITOSA");
            } catch (Exception e) {
                System.err.println("ERROR procesando JSON: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}