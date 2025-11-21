package com.elecciones.backend.controller;

import com.elecciones.backend.dto.VotoDistritalDTO;
import com.elecciones.backend.service.SupabaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data/district-votes")
public class VotoDistritalController {

    private final SupabaseService supabaseService;
    private static final String TABLE = "votos_distritales";

    public VotoDistritalController(SupabaseService supabaseService) {
        this.supabaseService = supabaseService;
    }

    @GetMapping
    public List<VotoDistritalDTO> getAll() {
        return supabaseService.getAll(TABLE, VotoDistritalDTO.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VotoDistritalDTO> getById(@PathVariable String id) {
        VotoDistritalDTO voto = supabaseService.getById(TABLE, id, VotoDistritalDTO.class);
        return voto != null 
            ? ResponseEntity.ok(voto) 
            : ResponseEntity.notFound().build();
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<VotoDistritalDTO> getByDni(@PathVariable String dni) {
        VotoDistritalDTO voto = supabaseService.getVotoByDni(TABLE, dni, VotoDistritalDTO.class);
        return voto != null 
            ? ResponseEntity.ok(voto) 
            : ResponseEntity.notFound().build();
    }

    @GetMapping("/candidato/{candidatoId}")
    public List<VotoDistritalDTO> getByCandidato(@PathVariable String candidatoId) {
        return supabaseService.getVotosByCandidato(TABLE, candidatoId, VotoDistritalDTO.class);
    }

    @GetMapping("/ubicacion")
    public List<VotoDistritalDTO> getByUbicacion(
            @RequestParam String departamento,
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) String distrito) {
        return supabaseService.getVotosByUbicacion(
            TABLE, departamento, provincia, distrito, VotoDistritalDTO.class
        );
    }

    @PostMapping
    public VotoDistritalDTO create(@RequestBody VotoDistritalDTO voto) {
        return supabaseService.insert(TABLE, voto, VotoDistritalDTO.class);
    }

    @PatchMapping("/{id}")
    public VotoDistritalDTO update(@PathVariable String id, @RequestBody VotoDistritalDTO voto) {
        return supabaseService.update(TABLE, id, voto, VotoDistritalDTO.class);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        supabaseService.delete(TABLE, id);
        return ResponseEntity.noContent().build();
    }
}