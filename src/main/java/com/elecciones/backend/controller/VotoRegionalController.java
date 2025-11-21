package com.elecciones.backend.controller;

import com.elecciones.backend.dto.VotoRegionalDTO;
import com.elecciones.backend.service.SupabaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data/regional-votes")
public class VotoRegionalController {

    private final SupabaseService supabaseService;
    private static final String TABLE = "votos_regionales";

    public VotoRegionalController(SupabaseService supabaseService) {
        this.supabaseService = supabaseService;
    }

    @GetMapping
    public List<VotoRegionalDTO> getAll() {
        return supabaseService.getAll(TABLE, VotoRegionalDTO.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VotoRegionalDTO> getById(@PathVariable String id) {
        VotoRegionalDTO voto = supabaseService.getById(TABLE, id, VotoRegionalDTO.class);
        return voto != null 
            ? ResponseEntity.ok(voto) 
            : ResponseEntity.notFound().build();
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<VotoRegionalDTO> getByDni(@PathVariable String dni) {
        VotoRegionalDTO voto = supabaseService.getVotoByDni(TABLE, dni, VotoRegionalDTO.class);
        return voto != null 
            ? ResponseEntity.ok(voto) 
            : ResponseEntity.notFound().build();
    }

    @GetMapping("/candidato/{candidatoId}")
    public List<VotoRegionalDTO> getByCandidato(@PathVariable String candidatoId) {
        return supabaseService.getVotosByCandidato(TABLE, candidatoId, VotoRegionalDTO.class);
    }

    @GetMapping("/ubicacion")
    public List<VotoRegionalDTO> getByUbicacion(
            @RequestParam String departamento,
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) String distrito) {
        return supabaseService.getVotosByUbicacion(
            TABLE, departamento, provincia, distrito, VotoRegionalDTO.class
        );
    }

    @PostMapping
    public VotoRegionalDTO create(@RequestBody VotoRegionalDTO voto) {
        return supabaseService.insert(TABLE, voto, VotoRegionalDTO.class);
    }

    @PatchMapping("/{id}")
    public VotoRegionalDTO update(@PathVariable String id, @RequestBody VotoRegionalDTO voto) {
        return supabaseService.update(TABLE, id, voto, VotoRegionalDTO.class);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        supabaseService.delete(TABLE, id);
        return ResponseEntity.noContent().build();
    }
}