package com.elecciones.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)  // ← No envía campos null
public class VotanteDTO {
    private String id;
    private String dni;
    private String nombres;
    
    @JsonProperty("apellido_paterno")
    private String apellidoPaterno;
    
    @JsonProperty("apellido_materno")
    private String apellidoMaterno;
    
    // ← SOLO para lectura, nunca se envía en POST/PATCH
    @JsonProperty(value = "nombre_completo", access = JsonProperty.Access.READ_ONLY)
    private String nombreCompleto;
    
    private String departamento;
    private String provincia;
    private String distrito;
    private String direccion;
    
    @JsonProperty("direccion_completa")
    private String direccionCompleta;
    
    @JsonProperty("ubigeo_reniec")
    private String ubigeoReniec;
    
    @JsonProperty("ubigeo_sunat")
    private String ubigeoSunat;
    
    private String telefono;
    private String email;
    private String estado;
    
    @JsonProperty(value = "created_at", access = JsonProperty.Access.READ_ONLY)
    private String createdAt;
    
    @JsonProperty(value = "updated_at", access = JsonProperty.Access.READ_ONLY)
    private String updatedAt;
}