package com.pfe.learningplatform.service;

import com.pfe.learningplatform.dto.*;

import com.pfe.learningplatform.exception.BadRequestException;
import com.pfe.learningplatform.exception.DuplicateResourceException;
import com.pfe.learningplatform.exception.ResourceNotFoundException;

import com.pfe.learningplatform.model.Role;
import com.pfe.learningplatform.model.User;

import com.pfe.learningplatform.repository.UserRepository;

import com.pfe.learningplatform.security.JwtService;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    @Value("${admin.secret-code}")
    private String adminSecretCode;

    // injection
    public AuthService(

            UserRepository userRepository,

            JwtService jwtService,

            PasswordEncoder passwordEncoder
    ) {

        this.userRepository = userRepository;

        this.jwtService = jwtService;

        this.passwordEncoder = passwordEncoder;
    }

    /*
     * =========================================
     * REGISTER
     * =========================================
     */

    public String register(
            RegisterRequest request
    ) {

        // email existe déjà
        if (

                userRepository.findByEmail(
                        request.getEmail()
                ).isPresent()
        ) {

            throw new DuplicateResourceException(
                    "Email déjà utilisé"
            );
        }

        /*
         * =====================================
         * DEFAULT ROLE
         * =====================================
         */

        Role role = Role.USER;

        /*
         * =====================================
         * ADMIN VERIFICATION
         * =====================================
         */

        if (

                request.getRole() == Role.ADMIN
        ) {

            if (

                    request.getAdminCode() == null

                            ||

                            !request.getAdminCode()
                                    .equals(adminSecretCode)
            ) {

                throw new BadRequestException(
                        "Code admin invalide"
                );
            }

            role = Role.ADMIN;
        }

        /*
         * =====================================
         * CREATE USER
         * =====================================
         */

        User user = User.builder()

                .nom(
                        request.getNom()
                )

                .prenom(
                        request.getPrenom()
                )

                .email(
                        request.getEmail()
                )

                .password(

                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )

                .level("beginner")

                .role(role)

                .build();

        userRepository.save(user);

        return "Utilisateur enregistré avec succès";
    }

    /*
     * =========================================
     * LOGIN
     * =========================================
     */

    public AuthResponse login(
            LoginRequest request
    ) {

        User user = userRepository

                .findByEmail(
                        request.getEmail()
                )

                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Utilisateur non trouvé"
                        )
                );

        /*
         * =====================================
         * PASSWORD CHECK
         * =====================================
         */

        if (

                !passwordEncoder.matches(

                        request.getPassword(),

                        user.getPassword()
                )
        ) {

            throw new BadRequestException(
                    "Mot de passe incorrect"
            );
        }

        /*
         * =====================================
         * GENERATE JWT
         * =====================================
         */

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return AuthResponse.builder()

                .token(token)

                .role(
                        user.getRole().name()
                )

                .email(
                        user.getEmail()
                )

                .userId(
                        user.getId()
                )

                .nom(
                        user.getNom()
                )

                .prenom(
                        user.getPrenom()
                )

                .level(
                        user.getLevel()
                )

                .build();
    }

    /*
     * =========================================
     * UPDATE PROFILE
     * =========================================
     */

    public String updateProfile(

            Long id,

            UpdateProfileRequest request
    ) {

        User user = userRepository.findById(id)

                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Utilisateur non trouvé"
                        )
                );

        /*
         * =====================================
         * UPDATE NOM
         * =====================================
         */

        if (

                request.getNom() != null

                        &&

                        !request.getNom().isEmpty()
        ) {

            user.setNom(
                    request.getNom()
            );
        }

        /*
         * =====================================
         * UPDATE PRENOM
         * =====================================
         */

        if (

                request.getPrenom() != null

                        &&

                        !request.getPrenom().isEmpty()
        ) {

            user.setPrenom(
                    request.getPrenom()
            );
        }

        /*
         * =====================================
         * UPDATE EMAIL
         * =====================================
         */

        if (

                request.getEmail() != null

                        &&

                        !request.getEmail().isEmpty()
        ) {

            user.setEmail(
                    request.getEmail()
            );
        }

        /*
         * =====================================
         * UPDATE PASSWORD
         * =====================================
         */

        if (

                request.getPassword() != null

                        &&

                        !request.getPassword().isEmpty()
        ) {

            user.setPassword(

                    passwordEncoder.encode(
                            request.getPassword()
                    )
            );
        }

        userRepository.save(user);

        return "Profil mis à jour";
    }

    /*
     * =========================================
     * DELETE USER
     * =========================================
     */

    public String deleteUser(Long id) {

        User user = userRepository.findById(id)

                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Utilisateur non trouvé"
                        )
                );

        userRepository.delete(user);

        return "Utilisateur supprimé";
    }
}