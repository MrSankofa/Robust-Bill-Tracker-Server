package com.billtracker.billtracker.auth;

import com.billtracker.billtracker.model.User;
import com.billtracker.billtracker.repository.UserRepository;
import com.billtracker.billtracker.security.CustomUserDetails;
import com.billtracker.billtracker.security.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
  }

  public String register(RegisterRequest request) {
    User user = new User();
    user.setId(UUID.randomUUID().toString());
    user.setUsername(request.getUsername());
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    userRepository.save(user);
    return "User registered successfully";
  }

  public String login(AuthRequest request) {
    User user = userRepository.findByUsername(request.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException(request.getUsername()));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new BadCredentialsException("Invalid password");
    }

    CustomUserDetails userDetails = new CustomUserDetails(user);
    return jwtService.generateToken(userDetails);
  }
}
