package com.elecciones.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CandidatoDTO {
    private String id;
    private String nombre;
    private String partido;
    
    @JsonProperty("tipo_eleccion")
    private String tipoEleccion;
    
    private String descripcion;
    
    @JsonProperty("image_url")
    private String imageUrl;
    
    // ✅ JSONB se mapea como JsonNode (puede ser objeto, array o cualquier JSON)
    private JsonNode propuestas;
    
    // ✅ TEXT se mapea como String
    private String experiencia;
    private String educacion;
    
    @JsonProperty("is_active")
    private Boolean isActive;
    
    @JsonProperty("created_at")
    private String createdAt;
    
    @JsonProperty("updated_at")
    private String updatedAt;
}