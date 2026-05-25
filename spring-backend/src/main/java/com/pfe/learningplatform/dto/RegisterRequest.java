package com.pfe.learningplatform.dto;

import com.pfe.learningplatform.model.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    /*
     * =========================================
     * USER FIRST NAME
     * =========================================
     */

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    /*
     * =========================================
     * USER LAST NAME
     * =========================================
     */

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    /*
     * =========================================
     * USER EMAIL
     * =========================================
     */

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    /*
     * =========================================
     * USER PASSWORD
     * =========================================
     */

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(
            min = 6,
            message = "Le mot de passe doit contenir au moins 6 caractères"
    )
    private String password;

    /*
     * =========================================
     * USER ROLE
     * USER / ADMIN
     * =========================================
     */

    private Role role;

    /*
     * =========================================
     * ADMIN SECRET CODE
     * =========================================
     */

    private String adminCode;
}