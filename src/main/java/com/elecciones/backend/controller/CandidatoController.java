package com.elecciones.backend.controller;

import com.elecciones.backend.dto.CandidatoDTO;
import com.elecciones.backend.service.SupabaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data/candidates")
public class CandidatoController {

    private final SupabaseService supabaseService;
    private static final String TABLE = "candidatos";

    public CandidatoController(SupabaseService supabaseService) {
        this.supabaseService = supabaseService;
    }

    @GetMapping
    public List<CandidatoDTO> getAll() {
        return supabaseService.getAll(TABLE, CandidatoDTO.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidatoDTO> getById(@PathVariable String id) {
        CandidatoDTO candidato = supabaseService.getById(TABLE, id, CandidatoDTO.class);
        return candidato != null 
            ? ResponseEntity.ok(candidato) 
            : ResponseEntity.notFound().build();
    }

    @GetMapping("/tipo/{tipoEleccion}")
    public List<CandidatoDTO> getByTipoEleccion(@PathVariable String tipoEleccion) {
        return supabaseService.getAllByFilter(
            TABLE, 
            "tipo_eleccion=eq." + tipoEleccion, 
            CandidatoDTO.class
        );
    }

    @PostMapping
    public CandidatoDTO create(@RequestBody CandidatoDTO candidato) {
        return supabaseService.insert(TABLE, candidato, CandidatoDTO.class);
    }

    @PatchMapping("/{id}")
    public CandidatoDTO update(@PathVariable String id, @RequestBody CandidatoDTO candidato) {
        return supabaseService.update(TABLE, id, candidato, CandidatoDTO.class);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        supabaseService.delete(TABLE, id);
        return ResponseEntity.noContent().build();
    }
}