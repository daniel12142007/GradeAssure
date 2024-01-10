package com.example.gradeassure.service.auth;

import com.example.gradeassure.config.JwtUtils;
import com.example.gradeassure.dto.request.RegisterUserRequest;
import com.example.gradeassure.dto.response.JWTResponse;
import com.example.gradeassure.model.User;
import com.example.gradeassure.model.enums.Role;
import com.example.gradeassure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JavaMailSender javaMailSender;

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;


    public JWTResponse login(String email, String password) {
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found for email: " + email));

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new BadCredentialsException("Invalid password");
            }

            String token = jwtUtils.generateToken(user.getEmail());

            return new JWTResponse(
                    "CUSTOM " + user.getEmail(),
                        token,
                        "login",
                    user.getRole()
            );
        } catch (UsernameNotFoundException | BadCredentialsException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Internal Server Error");
        }
    }


    public JWTResponse register(RegisterUserRequest request) {
        User user = new User();
        check(request.getPassword());
        String code = generateRandomPassword();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(request.getEmail());
        message.setSubject("Code : " + code);
        message.setText("Сброс пароля");
        javaMailSender.send(message);
        user.setEmail(request.getEmail());
        user.setCode(code);
        user.setFullName(request.getFullName());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        if (userRepository.existsByEmail(request.getEmail()))
            throw new RuntimeException("found email:" + request.getEmail() + " email");
        userRepository.save(user);
        String token = jwtUtils.generateToken(user.getEmail());
        return new JWTResponse(
                user.getEmail(),
                token,
                "register",
                user.getRole()
        );
    }


    private boolean check(String password) {
        if (!isLengthValid(password)) {
            throw new RuntimeException(("Пароль должен быть хотя бы 6 символов"));
        } else if (!containsUpperCase(password)) {
            throw new RuntimeException(("Пароль должен содержать хотя бы 1 заглавную букву"));
        } else if (!containsDigit(password)) {
            throw new RuntimeException(("Пароль должен содержать хотя бы 1 цифру"));
        }
        return true;
    }

    private String generateRandomPassword() {
        Random random = new Random();
        return random.nextInt(0, 9) + "" + random.nextInt(0, 9) + "" + random.nextInt(0, 9) + "" + random.nextInt(0, 9) + "" + random.nextInt(0, 9) + "" + random.nextInt(0, 9);
    }

    private boolean isLengthValid(String password) {
        return password.length() >= 6;
    }

    private boolean containsUpperCase(String password) {
        return !password.equals(password.toLowerCase());
    }

    private boolean containsDigit(String password) {
        return password.chars().anyMatch(Character::isDigit);
    }
}