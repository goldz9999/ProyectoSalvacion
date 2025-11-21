package com.elecciones.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class SupabaseService {

    private final WebClient supabaseClient;

    public SupabaseService(WebClient supabaseClient) {
        this.supabaseClient = supabaseClient;
    }

    // ========== MÉTODOS GENÉRICOS ==========

    /**
     * GET - Obtener todos los registros de una tabla
     */
    public <T> List<T> getAll(String table, Class<T> clazz) {
        return supabaseClient.get()
                .uri("/" + table)
                .retrieve()
                .bodyToFlux(clazz)
                .collectList()
                .block();
    }

    /**
     * GET - Obtener un registro por ID
     */
    public <T> T getById(String table, String id, Class<T> clazz) {
        return supabaseClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/" + table)
                        .queryParam("id", "eq." + id)
                        .build())
                .retrieve()
                .bodyToFlux(clazz)
                .blockFirst();
    }

    /**
     * GET - Obtener registros con filtro personalizado
     * Ejemplo: getAllByFilter("candidatos", "tipo_eleccion=eq.presidencial", CandidatoDTO.class)
     */
    public <T> List<T> getAllByFilter(String table, String filter, Class<T> clazz) {
        return supabaseClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/" + table)
                        .query(filter)
                        .build())
                .retrieve()
                .bodyToFlux(clazz)
                .collectList()
                .block();
    }

    /**
     * GET - Obtener UN registro con filtro (para búsquedas únicas como DNI)
     */
    public <T> T getOneByFilter(String table, String filter, Class<T> clazz) {
        return supabaseClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/" + table)
                        .query(filter)
                        .build())
                .retrieve()
                .bodyToFlux(clazz)
                .blockFirst();
    }

    /**
     * POST - Insertar un nuevo registro
     */
    public <T> T insert(String table, Object body, Class<T> clazz) {
        return supabaseClient.post()
                .uri("/" + table)
                .bodyValue(body)
                .retrieve()
                .bodyToFlux(clazz)
                .blockFirst();
    }

    /**
     * PATCH - Actualizar un registro por ID
     */
    public <T> T update(String table, String id, Object body, Class<T> clazz) {
        return supabaseClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path("/" + table)
                        .queryParam("id", "eq." + id)
                        .build())
                .bodyValue(body)
                .retrieve()
                .bodyToFlux(clazz)
                .blockFirst();
    }

    /**
     * DELETE - Eliminar un registro por ID
     */
    public void delete(String table, String id) {
        supabaseClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/" + table)
                        .queryParam("id", "eq." + id)
                        .build())
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    // ========== MÉTODOS ESPECÍFICOS PARA VOTOS (OPCIONAL) ==========

    /**
     * Obtener votos por DNI del votante
     */
    public <T> T getVotoByDni(String table, String dni, Class<T> clazz) {
        return getOneByFilter(table, "dni_votante=eq." + dni, clazz);
    }

    /**
     * Obtener todos los votos por candidato
     */
    public <T> List<T> getVotosByCandidato(String table, String candidatoId, Class<T> clazz) {
        return getAllByFilter(table, "candidato_id=eq." + candidatoId, clazz);
    }

    /**
     * Obtener votos por ubicación (departamento, provincia, distrito)
     */
    public <T> List<T> getVotosByUbicacion(String table, String departamento, 
                                            String provincia, String distrito, Class<T> clazz) {
        String filter = "departamento=eq." + departamento;
        if (provincia != null && !provincia.isEmpty()) {
            filter += "&provincia=eq." + provincia;
        }
        if (distrito != null && !distrito.isEmpty()) {
            filter += "&distrito=eq." + distrito;
        }
        return getAllByFilter(table, filter, clazz);
    }

    /**
     * Contar votos por candidato (usando función RPC de Supabase)
     * Nota: Requiere crear una función RPC en Supabase
     */
    public Integer contarVotosPorCandidato(String candidatoId) {
        // Este es un ejemplo si creas una función RPC en Supabase:
        // CREATE OR REPLACE FUNCTION contar_votos(candidate_id UUID)
        // RETURNS INTEGER AS $$
        //   SELECT COUNT(*) FROM votos_presidenciales WHERE candidato_id = candidate_id;
        // $$ LANGUAGE SQL;
        
        return supabaseClient.post()
                .uri("/rpc/contar_votos")
                .bodyValue("{\"candidate_id\":\"" + candidatoId + "\"}")
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
    }
}