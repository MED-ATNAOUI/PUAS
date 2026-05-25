package com.pfe.learningplatform.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProfileRequest {

    /*
     * =========================================
     * USER NAME
     * =========================================
     */

    private String nom;

    private String prenom;

    /*
     * =========================================
     * EMAIL
     * =========================================
     */

    private String email;

    /*
     * =========================================
     * PASSWORD
     * =========================================
     */

    private String password;
}