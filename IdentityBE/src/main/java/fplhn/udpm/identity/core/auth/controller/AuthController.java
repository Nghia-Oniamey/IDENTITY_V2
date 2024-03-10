package fplhn.udpm.identity.core.auth.controller;

import fplhn.udpm.identity.core.auth.model.request.RefreshTokenRequest;
import fplhn.udpm.identity.core.auth.service.AuthService;
import fplhn.udpm.identity.core.common.config.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin("*")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        ResponseObject response = authService.refreshToken(request);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/log-out")
    public ResponseEntity<?> logout(@RequestBody Long userId) {
        ResponseObject response = authService.logout(userId);
        return new ResponseEntity<>(response, response.getStatus());
    }

}
