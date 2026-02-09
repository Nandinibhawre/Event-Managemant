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

    // ✅ REGISTER
    public String register(RegisterRequest request) {

        String role = request.getRole().toUpperCase();

        if ("ADMIN".equals(role)) {
            Admin admin = new Admin();
            admin.setName(request.getName());
            admin.setEmail(request.getEmail());
            admin.setPassword(encoder.encode(request.getPassword()));
            admin.setCreatedAt(LocalDateTime.now());
            adminRepo.save(admin);
        }

        else if ("ORGANIZER".equals(role)) {
            Organizer org = new Organizer();
            org.setName(request.getName());
            org.setEmail(request.getEmail());
            org.setPassword(encoder.encode(request.getPassword()));
            org.setCreatedAt(LocalDateTime.now());
            organizerRepo.save(org);
        }

        else {
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(encoder.encode(request.getPassword()));
            user.setCreatedAt(LocalDateTime.now());
            userRepo.save(user);
        }

        return "Registered Successfully";
    }

    // ✅ LOGIN
    public AuthResponse login(LoginRequest request) {

        String role = request.getRole().toUpperCase();
        String token;
        String email;

        if ("ADMIN".equals(role)) {

            Admin admin = adminRepo.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Invalid credentials"));

            if (!encoder.matches(request.getPassword(), admin.getPassword()))
                throw new RuntimeException("Invalid credentials");

            email = admin.getEmail();

            token = jwtUtil.generateToken(
                    email,
                    admin.getAdminid(),
                    role
            );
        }

        else if ("ORGANIZER".equals(role)) {

            Organizer org = organizerRepo.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Invalid credentials"));

            if (!encoder.matches(request.getPassword(), org.getPassword()))
                throw new RuntimeException("Invalid credentials");

            email = org.getEmail();

            token = jwtUtil.generateToken(
                    email,
                    org.getOrganizerId(),
                    role
            );
        }

        else {

            User user = userRepo.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Invalid credentials"));

            if (!encoder.matches(request.getPassword(), user.getPassword()))
                throw new RuntimeException("Invalid credentials");

            email = user.getEmail();

            token = jwtUtil.generateToken(
                    email,
                    user.getUserid(),
                    role
            );
        }

        return new AuthResponse(token, email, role);
    }
}
