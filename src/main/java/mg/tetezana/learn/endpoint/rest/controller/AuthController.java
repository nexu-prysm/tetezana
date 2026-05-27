package mg.tetezana.learn.endpoint.rest.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import mg.tetezana.learn.repository.AppUserRepository;
import mg.tetezana.learn.repository.model.AppUser;
import mg.tetezana.learn.service.security.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final AppUserRepository userRepository;

  @PostMapping("/login")
  public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
    // Simple mock login for MVP phase.
    // In reality, verify passwordHash (e.g., using BCrypt).
    AppUser user =
        userRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (!user.getPasswordHash().equals(request.getPassword())) {
      throw new RuntimeException("Invalid credentials");
    }

    String token = authService.generateToken(user);
    return ResponseEntity.ok(new TokenResponse(token));
  }

  @Data
  public static class LoginRequest {
    private String email;
    private String password;
  }

  @Data
  @RequiredArgsConstructor
  public static class TokenResponse {
    private final String token;
  }
}
