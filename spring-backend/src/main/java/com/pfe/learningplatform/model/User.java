package com.pfe.learningplatform.model;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

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

    @Column(unique = true)
    private String email;

    /*
     * =========================================
     * PASSWORD
     * =========================================
     */

    private String password;

    /*
     * =========================================
     * USER LEVEL
     * =========================================
     */

    private String level;

    /*
     * =========================================
     * USER ROLE
     * =========================================
     */

    @Enumerated(EnumType.STRING)
    private Role role;
}