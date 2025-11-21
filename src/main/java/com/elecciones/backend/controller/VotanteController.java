package com.elecciones.backend.controller;

import com.elecciones.backend.dto.VotanteDTO;
import com.elecciones.backend.service.SupabaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data/voters")
public class VotanteController {

    private final SupabaseService supabaseService;
    private static final String TABLE = "votantes";

    public VotanteController(SupabaseService supabaseService) {
        this.supabaseService = supabaseService;
    }

    @GetMapping
    public List<VotanteDTO> getAll() {
        return supabaseService.getAll(TABLE, VotanteDTO.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VotanteDTO> getById(@PathVariable String id) {
        VotanteDTO votante = supabaseService.getById(TABLE, id, VotanteDTO.class);
        return votante != null 
            ? ResponseEntity.ok(votante) 
            : ResponseEntity.notFound().build();
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<VotanteDTO> getByDni(@PathVariable String dni) {
        VotanteDTO votante = supabaseService.getOneByFilter(
            TABLE, 
            "dni=eq." + dni, 
            VotanteDTO.class
        );
        return votante != null 
            ? ResponseEntity.ok(votante) 
            : ResponseEntity.notFound().build();
    }

    @PostMapping
    public VotanteDTO create(@RequestBody VotanteDTO votante) {
        return supabaseService.insert(TABLE, votante, VotanteDTO.class);
    }

    @PatchMapping("/{id}")
    public VotanteDTO update(@PathVariable String id, @RequestBody VotanteDTO votante) {
        return supabaseService.update(TABLE, id, votante, VotanteDTO.class);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        supabaseService.delete(TABLE, id);
        return ResponseEntity.noContent().build();
    }
}