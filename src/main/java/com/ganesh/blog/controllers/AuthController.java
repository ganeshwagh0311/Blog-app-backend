package com.ganesh.blog.controllers;

import com.ganesh.blog.entities.User;
import com.ganesh.blog.payload.JwtAuthRequest;
import com.ganesh.blog.payload.JwtAuthResponse;
import com.ganesh.blog.payload.UserDto;
import com.ganesh.blog.security.JwtTokenHelper;
import com.ganesh.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    // 🔐 Login API
    @PostMapping("/login")
    public ResponseEntity<?> createToken(@RequestBody JwtAuthRequest request) {
        logger.info("🔐 Received login request for username: {}", request.getUsername());

        try {
            // Authenticate user credentials
            authenticate(request.getUsername(), request.getPassword());

            // Generate JWT
            String token = jwtTokenHelper.generateToken(request.getUsername());
            logger.info("✅ Generated Token: {}", token);

            // Load user from database using userService instead of casting UserDetails
            User userEntity = userService.getUserByEmail(request.getUsername());
            UserDto userDto = mapper.map(userEntity, UserDto.class);

            // Prepare response
            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken("Bearer " + token);
            response.setUser(userDto);

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            logger.error("❌ Invalid credentials for username: {}", request.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception e) {
            logger.error("❌ Error during authentication: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during authentication");
        }
    }

    // 🔍 Authenticate user
    private void authenticate(String username, String password) {
        logger.info("🔍 Authenticating user: {}", username);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("✅ Authentication successful for user: {}", username);
        } catch (BadCredentialsException e) {
            logger.error("❌ Authentication failed for user: {}", username);
            throw new BadCredentialsException("Invalid credentials");
        } catch (Exception e) {
            logger.error("⚠️ Unexpected error during authentication", e);
            throw new RuntimeException("Unexpected authentication error");
        }
    }

    // 🆕 Register new user API
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        logger.info("📝 Registering new user: {}", userDto.getEmail());
        UserDto registeredUser = userService.registerNewUser(userDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}
