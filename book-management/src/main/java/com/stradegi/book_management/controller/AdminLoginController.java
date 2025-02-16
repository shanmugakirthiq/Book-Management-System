package com.stradegi.book_management.controller;

import com.stradegi.book_management.model.dto.AdminLoginDTO;
import com.stradegi.book_management.service.AdminLoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@Tag(name = "Admin Management", description = "Admin Login & Info")
public class AdminLoginController {

    private final AdminLoginService adminLoginService;

    @Operation(summary = "Admin Login", description = "Validates admin credentials and creates a session")
    @PostMapping("/login")
    public ResponseEntity<String> loginAdmin(
            @RequestBody Map<String, String> loginRequest,
            HttpServletRequest request
    ) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        log.info("Admin login attempt for username: {}", username);

        boolean isValid = adminLoginService.validateAdmin(username, password);

        if (isValid) {
            HttpSession session = request.getSession();
            session.setAttribute("admin", username);
            log.info("Admin session created for username: {}", username);
            return ResponseEntity.ok("Login successful");
        } else {
            log.warn("Invalid login attempt for username: {}", username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @Operation(summary = "Check Admin Session", description = "Checks if an admin session is active")
    @GetMapping("/session")
    public ResponseEntity<String> checkSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("admin") != null) {
            return ResponseEntity.ok("Session active");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No active session");
    }

    @Operation(summary = "Get Admin Details", description = "Fetch admin details by session username")
    @GetMapping("/details")
    public ResponseEntity<AdminLoginDTO> getAdminDetails(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("admin") != null) {
            String username = (String) session.getAttribute("admin");
            log.info("Fetching details for logged-in admin: {}", username);

            AdminLoginDTO adminDetails = adminLoginService.getAdminByUsername(username);
            return ResponseEntity.ok(adminDetails);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }


    @Operation(summary = "Admin Logout", description = "Destroys the admin session")
    @PostMapping("/logout")
    public ResponseEntity<String> logoutAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            log.info("Admin session destroyed.");
            return ResponseEntity.ok("Logged out successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No active session to log out from");
    }

@Operation(summary = "Get Admin Details", description = "Fetch admin details by username")
    @GetMapping("/{username}")
    public AdminLoginDTO getAdminDetails(@PathVariable String username) {
        log.info("Fetching admin details for username: {}", username);
        return adminLoginService.getAdminByUsername(username);
    }
}
