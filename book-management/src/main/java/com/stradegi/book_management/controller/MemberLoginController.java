package com.stradegi.book_management.controller;

import com.stradegi.book_management.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
@Tag(name = "Member Authentication", description = "Handles Member Login, Logout, and Password Management")
public class MemberLoginController {

    private final MemberService memberService;

    @Operation(summary = "Member Login", description = "Validates member credentials and creates a session")
    @PostMapping("/login")
    public ResponseEntity<String> loginMember(@RequestBody Map<String, String> loginRequest, HttpServletRequest request) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        log.info("Login attempt for email: {}", email);

        boolean isValid = memberService.validateMemberCredentials(email, password);
        if (isValid) {
            HttpSession session = request.getSession();
            session.setAttribute("member", email);

            // Check if it's the first login (temporary password still in effect)
            boolean firstLogin = memberService.isFirstLogin(email);
            if (firstLogin) {
                log.info("First login detected for email: {}", email);
                return ResponseEntity.ok("FIRST_TIME");
            } else {
                log.info("Member logged in successfully: {}", email);
                return ResponseEntity.ok("Login successful");
            }
        } else {
            log.warn("Invalid login attempt for email: {}", email);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @Operation(summary = "Change Password", description = "Allows a member to change their password after first login")
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String newPassword = requestBody.get("newPassword");

        Long memberId = memberService.getMemberIdByEmail(email);
        if (memberId != null) {
            memberService.changePassword(memberId, newPassword);
            log.info("Password updated successfully for email: {}", email);
            return ResponseEntity.ok("Password changed successfully");
        } else {
            log.error("Member not found for email: {}", email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member not found");
        }
    }

    @Operation(summary = "Logout", description = "Destroys the session for the member")
    @PostMapping("/logout")
    public ResponseEntity<String> logoutMember(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            log.info("Member logged out successfully.");
            return ResponseEntity.ok("Logged out successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No active session found");
        }
    }
}
