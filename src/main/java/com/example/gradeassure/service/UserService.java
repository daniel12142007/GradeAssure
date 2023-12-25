package com.example.gradeassure.service;

import com.example.gradeassure.dto.response.UserResponse;
import com.example.gradeassure.model.User;
import com.example.gradeassure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;


@RequiredArgsConstructor
@Service
public class UserService {

    private final JavaMailSender mailSender;

    private final UserRepository userRepository; // Замените на ваш репозиторий пользователей

    private final PasswordEncoder encoder;


    public UserResponse send(String email) {
        User user1 = userRepository.findByEmail(email).get();
        String code = generateRandomPassword();
        user1.setCode(code);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Code : " + code);
        message.setText("Сброс пароля");
        mailSender.send(message);
        userRepository.save(user1);
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user1.getId());
        userResponse.setFullName(user1.getFullName());
        userResponse.setEmail(user1.getEmail());
        userResponse.setCode(user1.getCode());
        return userResponse;
    }

    private String generateRandomPassword() {
        Random random = new Random();
        return random.nextInt(0, 9) + "" + random.nextInt(0, 9) + "" + random.nextInt(0, 9) + "" + random.nextInt(0, 9) + "" + random.nextInt(0, 9) + "" + random.nextInt(0, 9);
    }

    public UserResponse reset(String password, String password1, String email) {
        if (!password.equals(password1)) throw new RuntimeException("balnoy password");
        User user = userRepository.findByEmail(email).get();
        if (user.getCode() != null) throw new RuntimeException("code doljen bit null");
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFullName(user.getFullName());
        userResponse.setEmail(user.getEmail());
        userResponse.setCode(user.getCode());
        return userResponse;
    }

    public UserResponse isCodeValid(String email, String code) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.orElse(null);
        if (user == null)
            return null;
        if (user.getCode().equals(code)) {
            user.setCode(null);
            userRepository.save(user);
        }
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFullName(user.getFullName());
        userResponse.setEmail(user.getEmail());
        userResponse.setCode(user.getCode());
        return userResponse;
    }
}