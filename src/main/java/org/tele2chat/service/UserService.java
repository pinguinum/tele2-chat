package org.tele2chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.tele2chat.model.User;
import org.tele2chat.repository.UserRepository;
import org.tele2chat.security.AuthProviderFactory;
import org.tele2chat.security.AuthProviderService;
import org.tele2chat.security.ChatUserDetails;
import org.tele2chat.security.enums.SocialProvider;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthProviderFactory authProviderFactory;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, AuthProviderFactory authProviderFactory, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authProviderFactory = authProviderFactory;
        this.passwordEncoder = passwordEncoder;
    }

    public User authUserWithSocialMedia(OidcUser oidcUser, String socialProviderId) {
        AuthProviderService authProviderService = authProviderFactory.getAuthProviderService(
                SocialProvider.fromProviderId(socialProviderId));
        String email = authProviderService.getEmail(oidcUser);

        return userRepository.findByEmail(email).orElseGet(() -> createNewUser(oidcUser, authProviderService, email));
    }

    public User getAuthenticatedUser(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            String email = extractUsername(principal);
            return userRepository.findByEmail(email).orElseGet(User::new);
        }
        return new User();
    }

    public ResponseEntity<String> createUserByEmail(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email is already taken. Please try a different one.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created.");
    }

    private String extractUsername(Object principal) {
        if (principal instanceof OidcUser) {
            return ((OidcUser) principal).getEmail();
        } else if (principal instanceof UserDetails) {
            return ((ChatUserDetails) principal).getEmail();
        }
        return "";
    }

    private User createNewUser(OidcUser oidcUser, AuthProviderService authProviderService, String email) {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(authProviderService.getUsername(oidcUser));
        newUser.setPicture(authProviderService.getPicture(oidcUser));
        userRepository.save(newUser);
        return newUser;
    }
}
