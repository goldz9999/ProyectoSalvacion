package com.elecciones.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class VotoPresidencialDTO {
    private String id;
    
    @JsonProperty("votante_id")
    private String votanteId;
    
    @JsonProperty("candidato_id")
    private String candidatoId;
    
    @JsonProperty("dni_votante")
    private String dniVotante;
    
    @JsonProperty("fecha_voto")
    private String fechaVoto;
    
    private String departamento;
    private String provincia;
    private String distrito;
}