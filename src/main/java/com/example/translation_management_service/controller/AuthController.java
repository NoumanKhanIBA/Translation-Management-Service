package com.example.translation_management_service.controller;



import com.example.translation_management_service.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authManager,
                          JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }
    @Operation(summary = "Authenticate user",
            description = "Returns a JWT for valid credentials")
    @PostMapping("/login")
    public ResponseEntity<?> login( @Parameter(description="username and password")
            @RequestBody AuthRequest req) {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getUsername(), req.getPassword()
                )
        );

        String token = jwtUtil.generateToken(auth.getName());
        System.out.println("Printing token: "+token);
        return ResponseEntity.ok(token);
    }
}


class AuthRequest {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}




