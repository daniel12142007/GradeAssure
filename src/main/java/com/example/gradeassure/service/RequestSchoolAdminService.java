package com.example.gradeassure.service;

import com.example.gradeassure.dto.Response.RequestSchoolAdminResponse;
import com.example.gradeassure.model.RequestSchoolAdmin;
import com.example.gradeassure.model.SchoolAdmin;
import com.example.gradeassure.model.User;
import com.example.gradeassure.model.enums.Role;
import com.example.gradeassure.repository.RequestSchoolAdminRepository;
import com.example.gradeassure.repository.SchoolAdminRepository;
import com.example.gradeassure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RequestSchoolAdminService {

    private final RequestSchoolAdminRepository requestRepository;
    private final SchoolAdminRepository schoolAdminRepository;
    private final UserRepository userRepository;

    public RequestSchoolAdminResponse processRequestSchoolAdmin(int days) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User currentUser = userRepository.findByEmail(currentUsername).orElse(null);

        if (currentUser == null) {
            throw new AccessDeniedException("Текущий пользователь не найден");
        }

        SchoolAdmin schoolAdmin = new SchoolAdmin();
        schoolAdmin.setId(currentUser.getId());
        schoolAdmin.setEmail(currentUser.getEmail());
        schoolAdmin.setUser(currentUser);
        schoolAdminRepository.save(schoolAdmin);

        if (schoolAdmin == null) {
            throw new AccessDeniedException("Текущий пользователь не является администратором школы");
        }

        // Проверьте, отправлял ли администратор школы запрос ранее
        if (requestRepository.countBySchoolAdminAndAnsweredFalse(schoolAdmin).orElse(0L) >= 2) {
            throw new IllegalStateException("Вы не можете отправить более двух запросов.");
        }

        RequestSchoolAdmin request = new RequestSchoolAdmin();
        request.setDays(days);
        request.setDateCreated(LocalDateTime.now());
        request.setSchoolAdmin(schoolAdmin);

        requestRepository.save(request);

        RequestSchoolAdminResponse response = new RequestSchoolAdminResponse();
        response.setId(request.getId());
        response.setDays(request.getDays());
        response.setDateCreated(request.getDateCreated());
        response.setDateDeadline(request.getDateDeadline());

        return response;
    }

    public String approveRequests(List<Long> requestIds) {
        List<RequestSchoolAdmin> requests = requestRepository.findAllById(requestIds);

        requests.stream()
                .filter(request -> !request.isAnswered())
                .forEach(request -> {
                    request.setAnswered(true);
                    request.setDateAnswered(LocalDateTime.now());
                    request.setDateDeadline(LocalDateTime.now().plusDays(request.getDays()));

                    User user = request.getSchoolAdmin().getUser();
                    user.setRole(Role.ADMINSCHOOL);
                });

        requestRepository.saveAll(requests);

        return "Выбранные запросы одобрены.";
    }

    public String rejectRequests(List<Long> requestIds) {
        List<RequestSchoolAdmin> requests = requestRepository.findAllById(requestIds);

        requests.stream()
                .filter(request -> !request.isAnswered())
                .forEach(request -> {
                    request.setAnswered(true);
                    request.setDateAnswered(LocalDateTime.now());
                });

        requestRepository.saveAll(requests);

        return "Выбранные запросы отклонены.";
    }

    public String blockSchoolAdmins(List<Long> schoolAdminIds) {
        List<SchoolAdmin> schoolAdmins = schoolAdminRepository.findAllById(schoolAdminIds);

        schoolAdmins.stream()
                .filter(schoolAdmin -> !schoolAdmin.isBlocked())
                .forEach(schoolAdmin -> schoolAdmin.setBlocked(true));

        schoolAdminRepository.saveAll(schoolAdmins);

        return "Выбранные администраторы заблокированы.";
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void checkDeadlinesForSchoolAdmin() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<RequestSchoolAdmin> overdueRequests = requestRepository
                .findBySchoolAdminBlockedFalseAndDateDeadlineBeforeAndAnsweredFalse(currentDateTime);
        overdueRequests.forEach(request -> {
            SchoolAdmin schoolAdmin = request.getSchoolAdmin();
            User user = schoolAdmin.getUser();
            user.setRole(Role.USER);
            request.setOverdue(true);
        });

        requestRepository.saveAll(overdueRequests);
    }

}