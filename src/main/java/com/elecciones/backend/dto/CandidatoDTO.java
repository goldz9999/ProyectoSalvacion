package com.elecciones.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    
    private String propuestas;
    private String experiencia;
    private String educacion;
    
    @JsonProperty("is_active")
    private Boolean isActive;
    
    @JsonProperty("created_at")
    private String createdAt;
    
    @JsonProperty("updated_at")
    private String updatedAt;
}
