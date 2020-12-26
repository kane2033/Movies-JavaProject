package com.javaproject.movies.controller.user;

import com.javaproject.movies.security.jwt.JwtTokenProvider;
import com.javaproject.movies.dto.user.AuthenticationRequestDTO;
import com.javaproject.movies.dto.user.AuthenticationResponseDTO;
import com.javaproject.movies.dto.user.UserRequestDTO;
import com.javaproject.movies.entity.user.User;
import com.javaproject.movies.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "/api/auth/")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody AuthenticationRequestDTO requestDTO) {
        try {
            String username = requestDTO.getUsername();
            //дается аунтиф-я на основе отправленного логина и пароля
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDTO.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) { //если такого пользователя нет
                throw new UsernameNotFoundException("User with username " + username + "not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles()); //генерация токена

            AuthenticationResponseDTO responseDTO = new AuthenticationResponseDTO(username, token);

            return ResponseEntity.ok(responseDTO);
        }
        catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody UserRequestDTO requestDTO) {
        try {
            User newUser = new User(
                    requestDTO.getUsername(),
                    requestDTO.getFirstName(),
                    requestDTO.getLastName(),
                    requestDTO.getSecondName(),
                    requestDTO.getEmail(),
                    requestDTO.getPassword(),
                    requestDTO.getBirthday().isEmpty() ? null : LocalDate.parse(requestDTO.getBirthday())
            );

            userService.register(newUser);

            return ResponseEntity.ok("Successfully registered");
        }
        catch (AuthenticationException e) {
            throw new BadCredentialsException("Could not register new user");
        }
    }
}
