package br.com.craftlife.api.service;

import br.com.craftlife.api.controller.dto.AuthenticationRequest;
import br.com.craftlife.api.controller.dto.AuthenticationResponse;
import br.com.craftlife.api.controller.dto.RegisterRequest;
import br.com.craftlife.api.domain.User;
import br.com.craftlife.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = User.builder()
                .username(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        logger.info(String.format("Tentando autenticar usuário: %s", authenticationRequest.getUsername()));
        var user = userRepository.findByUsernameIgnoreCase(authenticationRequest.getUsername())
                .orElseThrow(() -> new NoSuchElementException(String.format("Usuário com nick %s não encontrado!", authenticationRequest.getUsername())));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        var jwtToken = jwtService.generateToken(user);

        logger.info(String.format("Usuário autenticado: %s", authenticationRequest.getUsername()));


        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(user.getRealname())
                .build();
    }

    public AuthenticationResponse authenticateWithRemoteAddress(String remoteAddress) {
        logger.info(String.format("Tentando autenticar usuário pelo fastlogin: %s", remoteAddress));

        var user = this.getUserByRemoteAddress(remoteAddress);
        if (user == null) return null;
        var jwtToken = jwtService.generateToken(user);

        logger.info(String.format("Usuário autenticado pelo fastlogin: %s, ip: %s", user.getRealname(), remoteAddress));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(user.getRealname())
                .build();
    }

    public User getUserByRemoteAddress(String remoteAddress) {
        List<User> users = userRepository.findByIpAndLastLoginAfter(remoteAddress, Date.from(Instant.now().minusSeconds(24 * 60 * 60)).getTime());
        if (users.size() != 1) {
            return null;
        }
        return users.get(0);
    }
}
