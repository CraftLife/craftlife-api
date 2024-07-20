package br.com.craftlife.api.controller;

import br.com.craftlife.api.controller.dto.AuthenticationRequest;
import br.com.craftlife.api.controller.dto.AuthenticationResponse;
import br.com.craftlife.api.controller.dto.RegisterRequest;
import br.com.craftlife.api.domain.User;
import br.com.craftlife.api.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok("Só é possível se registrar in-game!");
//        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/fast-login")
    public ResponseEntity<AuthenticationResponse> authenticateWithRemoteAddress(HttpServletRequest request) {
        String remoteAddress = request.getHeader("CF-Connecting-IP");
        if (remoteAddress == null || remoteAddress.isEmpty()) {
            remoteAddress = request.getRemoteAddr();
        }
        return ResponseEntity.ok(authenticationService.authenticateWithRemoteAddress(remoteAddress));
    }

    @PostMapping("/logout")
    public void logout() {
        // TODO
    }

    @GetMapping("/session")
    public User getCurrentUserSession(@AuthenticationPrincipal UserDetails userDetails) {
        return (User) userDetails;
    }
}
