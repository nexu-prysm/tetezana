package mg.tetezana.learn.endpoint.rest.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import mg.tetezana.learn.repository.AppUserRepository;
import mg.tetezana.learn.repository.model.AppUser;
import mg.tetezana.learn.service.security.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
  private final PasswordEncoder passwordEncoder;

  @PostMapping("/login")
  public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
    AppUser user =
        userRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
      throw new RuntimeException("Invalid credentials");
    }

    String token = authService.generateToken(user);

    ResponseCookie cookie = ResponseCookie.from("auth_token", token)
        .httpOnly(true)
        .secure(true) // In a real app, this might depend on the environment profile
        .path("/")
        .maxAge(authService.getJwtExpirationMs() / 1000)
        .sameSite("Strict")
        .build();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new TokenResponse("success"));
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
