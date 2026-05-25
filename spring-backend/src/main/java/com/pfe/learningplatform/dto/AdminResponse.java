package com.pfe.learningplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminResponse {

    /*
     * =========================================
     * USER INFO (sans mot de passe)
     * =========================================
     */

    private Long id;

    private String nom;

    private String prenom;

    private String email;

    private String level;

    private String role;
}
