package com.pfe.learningplatform.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    /*
     * =========================================
     * JWT TOKEN
     * =========================================
     */

    private String token;

    /*
     * =========================================
     * USER ROLE
     * =========================================
     */

    private String role;

    /*
     * =========================================
     * EMAIL
     * =========================================
     */

    private String email;

    /*
     * =========================================
     * USER ID
     * =========================================
     */

    private Long userId;

    /*
     * =========================================
     * USER NAME
     * =========================================
     */

    private String nom;

    private String prenom;

    /*
     * =========================================
     * USER LEVEL
     * =========================================
     */

    private String level;
}