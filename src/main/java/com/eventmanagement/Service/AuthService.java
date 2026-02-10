package com.eventmanagement.Service;

import com.eventmanagement.Model.Admin;
import com.eventmanagement.Model.Organizer;
import com.eventmanagement.Repository.AdminRepository;
import com.eventmanagement.Repository.OrganizerRepository;
import com.eventmanagement.Security.jwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eventmanagement.Dto.Request.LoginRequest;
import com.eventmanagement.Dto.Request.RegisterRequest;
import com.eventmanagement.Dto.Response.AuthResponse;
import com.eventmanagement.Model.User;
import com.eventmanagement.Repository.UserRepository;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdminRepository adminRepo;
    private final OrganizerRepository organizerRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final jwtUtil jwtUtil;

    public String register(RegisterRequest request) {

        String role = request.getRole().toUpperCase();
        String email = request.getEmail();

        // ✅ EMAIL ALREADY EXISTS CHECK (ALL ROLES)
        if (adminRepo.findByEmail(email).isPresent()
                || organizerRepo.findByEmail(email).isPresent()
                || userRepo.findByEmail(email).isPresent()) {

            throw new RuntimeException("Email already registered with another role");
        }

        if ("ADMIN".equals(role)) {
            Admin admin = new Admin();
            admin.setName(request.getName());
            admin.setEmail(email);
            admin.setPassword(encoder.encode(request.getPassword()));
            admin.setCreatedAt(LocalDateTime.now());
            adminRepo.save(admin);
        }

        else if ("ORGANIZER".equals(role)) {
            Organizer org = new Organizer();
            org.setName(request.getName());
            org.setEmail(email);
            org.setPassword(encoder.encode(request.getPassword()));
            org.setCreatedAt(LocalDateTime.now());
            organizerRepo.save(org);
        }

        else if ("USER".equals(role)) {
            User user = new User();
            user.setName(request.getName());
            user.setEmail(email);
            user.setPassword(encoder.encode(request.getPassword()));
            user.setCreatedAt(LocalDateTime.now());
            userRepo.save(user);
        }

        else {
            throw new RuntimeException("Invalid role");
        }

        return "Registered Successfully";
    }

    // ✅ LOGIN
    public AuthResponse login(LoginRequest request) {

        String role = request.getRole().toUpperCase();
        String email = request.getEmail();
        String password = request.getPassword();

        String token;

        // ===== ADMIN LOGIN =====
        if ("ADMIN".equals(role)) {

            Admin admin = adminRepo.findByEmail(email)
                    .orElseThrow(() ->
                            new RuntimeException("ADMIN account not found with this email")
                    );

            if (!encoder.matches(password, admin.getPassword())) {
                throw new RuntimeException("Invalid ADMIN password");
            }

            token = jwtUtil.generateToken(
                    admin.getEmail(),
                    admin.getAdminid(),
                    role
            );

            return new AuthResponse(token, admin.getEmail(), role);
        }

        // ===== ORGANIZER LOGIN =====
        else if ("ORGANIZER".equals(role)) {

            Organizer organizer = organizerRepo.findByEmail(email)
                    .orElseThrow(() ->
                            new RuntimeException("ORGANIZER account not found with this email")
                    );

            if (!encoder.matches(password, organizer.getPassword())) {
                throw new RuntimeException("Invalid ORGANIZER password");
            }

            token = jwtUtil.generateToken(
                    organizer.getEmail(),
                    organizer.getOrganizerId(),
                    role
            );

            return new AuthResponse(token, organizer.getEmail(), role);
        }

        // ===== USER LOGIN =====
        else if ("USER".equals(role)) {

            User user = userRepo.findByEmail(email)
                    .orElseThrow(() ->
                            new RuntimeException("USER account not found with this email")
                    );

            if (!encoder.matches(password, user.getPassword())) {
                throw new RuntimeException("Invalid USER password");
            }

            token = jwtUtil.generateToken(
                    user.getEmail(),
                    user.getUserid(),
                    role
            );

            return new AuthResponse(token, user.getEmail(), role);
        }

        // ===== INVALID ROLE =====
        else {
            throw new RuntimeException("Invalid role selected");
        }
    }
}
