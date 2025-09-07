package com.syncslot.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syncslot.config.JwtService;
import com.syncslot.token.Token;
import com.syncslot.token.TokenRepository;
import com.syncslot.token.TokenType;
import com.syncslot.user.User;
import com.syncslot.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Logger;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    Logger log = Logger.getLogger(AuthenticationService.class.getName());
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
        var jwt = jwtService.generateToken(user, false);
        var refreshToken = jwtService.generateRefreshToken(user);
//        saveUserToken(savedUser, jwt);

        return AuthenticationResponse.builder()
                .token(jwt)
                .refreshToken(refreshToken)
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwt = jwtService.generateToken(user, request.isRememberMe());
        var refreshToken = jwtService.generateRefreshToken(user);
//        revokeAllUserTokens(user);
        saveUserToken(user, jwt);

        return AuthenticationResponse.builder()
                .token(jwt)
                .refreshToken(refreshToken)
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .build();
    }

    public AuthenticationResponse authenticateGoogleAccount(RegisterRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElse(userRepository.save(User.builder()
                        .email(request.getEmail())
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .password(null)
                        .build()));

        //update user info

        var jwt = jwtService.generateToken(user, false);
        var refreshToken = jwtService.generateRefreshToken(user);
//        revokeAllUserTokens(user);
        saveUserToken(user, jwt);

        return AuthenticationResponse.builder()
                .token(jwt)
                .refreshToken(refreshToken)
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .build();
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken (user, false);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .token(accessToken)
                        .refreshToken(refreshToken)
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .username(user.getUsername())
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public Boolean checkIfLogged(HttpServletRequest request) {
        final String authHeader = request.getHeader(AUTHORIZATION);
        final String userEmail;
        final String jwt;
        final String validHeaderPrefix = "Bearer ";

        if (authHeader == null || !authHeader.startsWith(validHeaderPrefix)) {
            log.info("No Authentication header was present");
            return false;
        }
        jwt = authHeader.substring(validHeaderPrefix.length());
        userEmail = jwtService.extractUsername(jwt);

        if (userEmail != null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            var isTokenValid = tokenRepository.findByToken(jwt)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);

            return jwtService.isTokenValid(jwt, userDetails) && isTokenValid;
        }

        return false;
    }

    private void saveUserToken(User user, String jwt) {
        var token = Token.builder()
                .user(user)
                .token(jwt)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());

        if (validUserTokens.isEmpty()) return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
