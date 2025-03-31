package com.modsen.pizzap.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modsen.pizzap.config.JwtService;
import com.modsen.pizzap.dto.UserDTO;
import com.modsen.pizzap.exception.DuplicateResourceException;
import com.modsen.pizzap.exception.ResourceNotFoundException;
import com.modsen.pizzap.exception.error.ErrorMessages;
import com.modsen.pizzap.models.Role;
import com.modsen.pizzap.models.RoleName;
import com.modsen.pizzap.models.User;
import com.modsen.pizzap.repositories.RoleRepository;
import com.modsen.pizzap.repositories.UserRepository;
import com.modsen.pizzap.token.Token;
import com.modsen.pizzap.token.TokenRepository;
import com.modsen.pizzap.token.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.modsen.pizzap.config.SecurityUser;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse register(UserDTO request) {
        checkUserExistence(request.email());

        Role defaultRole = findRoleByName(RoleName.CUSTOMER);
        UserDetails userDetails = buildUser(request, defaultRole);

        User savedUser = userRepository.save(((SecurityUser) userDetails).getUser());
        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        saveUserToken(savedUser, accessToken);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = findUserByEmail(request.getEmail());
        UserDetails userDetails = new SecurityUser(user);
        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void revokeAllUserTokens(User user){
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);

        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        final AuthenticationResponse authResponse;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUserEmail(refreshToken);

        if (userEmail != null) {
            User user = findUserByEmail(userEmail);
            UserDetails userDetails = new SecurityUser(user);

            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                String newAccessToken = jwtService.generateToken(userDetails);
                String newRefreshToken = jwtService.generateRefreshToken(userDetails);
                revokeAllUserTokens(user);
                saveUserToken(user, newAccessToken);

                authResponse = AuthenticationResponse.builder()
                        .accessToken(newAccessToken)
                        .refreshToken(newRefreshToken)
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private Role findRoleByName(RoleName roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.ROLE_NOT_FOUND_MESSAGE, roleName)));
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND_MESSAGE, email)));
    }

    private void checkUserExistence(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException(String.format(ErrorMessages.DUPLICATE_RESOURCE_MESSAGE, "User", "email"));
        }
    }

    private UserDetails buildUser(UserDTO request, Role role) {
        User user = User.builder()
                .userName(request.userName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(role)
                .build();
        return new SecurityUser(user);
    }
}
