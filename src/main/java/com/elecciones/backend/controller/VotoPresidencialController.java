package com.elecciones.backend.controller;

import com.elecciones.backend.dto.VotoPresidencialDTO;
import com.elecciones.backend.service.SupabaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data/presidential-votes")
public class VotoPresidencialController {

    private final SupabaseService supabaseService;
    private static final String TABLE = "votos_presidenciales";

    public VotoPresidencialController(SupabaseService supabaseService) {
        this.supabaseService = supabaseService;
    }

    @GetMapping
    public List<VotoPresidencialDTO> getAll() {
        return supabaseService.getAll(TABLE, VotoPresidencialDTO.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VotoPresidencialDTO> getById(@PathVariable String id) {
        VotoPresidencialDTO voto = supabaseService.getById(TABLE, id, VotoPresidencialDTO.class);
        return voto != null 
            ? ResponseEntity.ok(voto) 
            : ResponseEntity.notFound().build();
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<VotoPresidencialDTO> getByDni(@PathVariable String dni) {
        VotoPresidencialDTO voto = supabaseService.getVotoByDni(TABLE, dni, VotoPresidencialDTO.class);
        return voto != null 
            ? ResponseEntity.ok(voto) 
            : ResponseEntity.notFound().build();
    }

    @GetMapping("/candidato/{candidatoId}")
    public List<VotoPresidencialDTO> getByCandidato(@PathVariable String candidatoId) {
        return supabaseService.getVotosByCandidato(TABLE, candidatoId, VotoPresidencialDTO.class);
    }

    @PostMapping
    public VotoPresidencialDTO create(@RequestBody VotoPresidencialDTO voto) {
        return supabaseService.insert(TABLE, voto, VotoPresidencialDTO.class);
    }

    @PatchMapping("/{id}")
    public VotoPresidencialDTO update(@PathVariable String id, @RequestBody VotoPresidencialDTO voto) {
        return supabaseService.update(TABLE, id, voto, VotoPresidencialDTO.class);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        supabaseService.delete(TABLE, id);
        return ResponseEntity.noContent().build();
    }
}