package com.modsen.pizzap.services.Impl;

import com.modsen.pizzap.config.SecurityUser;
import com.modsen.pizzap.exception.error.ErrorMessages;
import com.modsen.pizzap.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new SecurityUser(
                userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND_MESSAGE, email)))
        );
    }
}
