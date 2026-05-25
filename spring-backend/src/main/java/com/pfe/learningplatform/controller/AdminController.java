package com.pfe.learningplatform.controller;

import com.pfe.learningplatform.dto.AdminResponse;
import com.pfe.learningplatform.dto.DashboardStatsResponse;

import com.pfe.learningplatform.service.AdminService;
import com.pfe.learningplatform.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {

    private final AuthService authService;

    private final AdminService adminService;

    /*
     * =========================================
     * GET ALL USERS
     * ADMIN ONLY
     * =========================================
     */

    @GetMapping("/users")
    public ResponseEntity<List<AdminResponse>>
    getAllUsers() {

        List<AdminResponse> users =
                adminService.getAllUsers();

        return ResponseEntity.ok(users);
    }

    /*
     * =========================================
     * GET USER BY ID
     * ADMIN ONLY
     * =========================================
     */

    @GetMapping("/users/{id}")
    public ResponseEntity<AdminResponse>
    getUserById(

            @PathVariable Long id
    ) {

        AdminResponse user =
                adminService.getUserById(id);

        return ResponseEntity.ok(user);
    }

    /*
     * =========================================
     * GET DASHBOARD STATS
     * ADMIN ONLY
     * =========================================
     */

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsResponse>
    getDashboardStats() {

        DashboardStatsResponse stats =
                adminService.getDashboardStats();

        return ResponseEntity.ok(stats);
    }

    /*
     * =========================================
     * DELETE USER
     * ADMIN ONLY
     * =========================================
     */

    @DeleteMapping("/user/{id}")
    public String deleteUser(

            @PathVariable Long id
    ) {

        return authService.deleteUser(id);
    }
}