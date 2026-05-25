package com.pfe.learningplatform.controller;

import com.pfe.learningplatform.dto.*;

import com.pfe.learningplatform.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    // injection
    public AuthController(
            AuthService authService
    ) {

        this.authService = authService;
    }

    /*
     * =========================================
     * REGISTER
     * =========================================
     */

    @PostMapping("/register")
    public String register(

            @Valid
            @RequestBody
            RegisterRequest request
    ) {

        return authService.register(request);
    }

    /*
     * =========================================
     * LOGIN
     * =========================================
     */

    @PostMapping("/login")
    public AuthResponse login(

            @Valid
            @RequestBody
            LoginRequest request
    ) {

        return authService.login(request);
    }

    /*
     * =========================================
     * UPDATE PROFILE
     * =========================================
     */

    @PutMapping("/profile/{id}")
    public String updateProfile(

            @PathVariable Long id,

            @RequestBody
            UpdateProfileRequest request
    ) {

        return authService.updateProfile(
                id,
                request
        );
    }
}