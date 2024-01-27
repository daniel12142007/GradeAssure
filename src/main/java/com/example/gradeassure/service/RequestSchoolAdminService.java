package com.example.gradeassure.service;

import com.example.gradeassure.dto.response.BlockedSchoolAdminResponse;
import com.example.gradeassure.dto.response.RequestSchoolAdminResponse;
import com.example.gradeassure.dto.response.SchoolAdminResponse;
import com.example.gradeassure.dto.response.RequestSchoolAdminResponse;
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

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RequestSchoolAdminService {

    private final RequestSchoolAdminRepository requestSchoolAdminRepository;
    private final SchoolAdminRepository schoolAdminRepository;
    private final UserRepository userRepository;

    public RequestSchoolAdminResponse processRequestSchoolAdmin(int days, String email) {
        User currentUser = userRepository.findByEmail(email).orElse(null);

        if (currentUser == null) {
            throw new AccessDeniedException("Текущий пользователь не найден");
        }

        SchoolAdmin schoolAdmin = schoolAdminRepository.findByEmail(email).orElse(null);

        if (requestSchoolAdminRepository.countBySchoolAdminAndAnsweredFalse(schoolAdmin).orElse(0L) >= 2) {
            throw new IllegalStateException("Вы не можете отправить более двух запросов.");
        }

        RequestSchoolAdmin request = new RequestSchoolAdmin();
        request.setDays(days);
        request.setDateCreated(LocalDateTime.now());
        request.setSchoolAdmin(schoolAdmin);
        requestSchoolAdminRepository.save(request);

        RequestSchoolAdminResponse response = new RequestSchoolAdminResponse();
        response.setId(request.getId());
        response.setDays(request.getDays());
        response.setDateCreated(request.getDateCreated());
        response.setDateDeadline(request.getDateDeadline());
        return response;
    }

    public String approveRequests(List<Long> requestIds) {
        List<RequestSchoolAdmin> requests = requestSchoolAdminRepository.findAllById(requestIds);

        requests.stream()
                .filter(request -> !request.isAnswered())
                .forEach(request -> {
                    request.setAnswered(true);
                    request.setDateAnswered(LocalDateTime.now());
                    request.setDateDeadline(LocalDateTime.now().plusDays(request.getDays()));

                    User user = request.getSchoolAdmin().getUser();
                    user.setRole(Role.ADMINSCHOOL);
                });

        requestSchoolAdminRepository.saveAll(requests);

        return "Выбранные запросы одобрены.";
    }

    public String rejectRequests(List<Long> requestIds) {
        List<RequestSchoolAdmin> requests = requestSchoolAdminRepository.findAllById(requestIds);

        requests.stream()
                .filter(request -> !request.isAnswered())
                .forEach(request -> {
                    request.setAnswered(true);
                    request.setDateAnswered(LocalDateTime.now());
                });

        requestSchoolAdminRepository.saveAll(requests);

        return "Выбранные запросы отклонены.";
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void checkDeadlinesForSchoolAdmin() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<RequestSchoolAdmin> overdueRequests = requestSchoolAdminRepository
                .findBySchoolAdminBlockedFalseAndDateDeadlineBeforeAndAnsweredFalse(currentDateTime);
        overdueRequests.forEach(request -> {
            SchoolAdmin schoolAdmin = request.getSchoolAdmin();
            User user = schoolAdmin.getUser();
            user.setRole(Role.USER);
            request.setOverdue(true);
        });

        requestSchoolAdminRepository.saveAll(overdueRequests);
    }

    public List<SchoolAdminResponse> getAllUnblockedSchoolAdmins() {
        List<SchoolAdmin> unblockedSchoolAdmins = schoolAdminRepository.findAllUnblockedSchoolAdmins();

        return unblockedSchoolAdmins.stream()
                .map(schoolAdmin -> new SchoolAdminResponse(schoolAdmin.getId(), schoolAdmin.getFullName(), schoolAdmin.getEmail()))
                .collect(Collectors.toList());
    }

    public List<SchoolAdminResponse> blockSchoolAdmins(List<Long> schoolAdminIds) {
        List<SchoolAdmin> schoolAdmins = schoolAdminRepository.findAllById(schoolAdminIds);

        List<SchoolAdminResponse> responses = schoolAdmins.stream()
                .filter(schoolAdmin -> !schoolAdmin.isBlocked())
                .peek(schoolAdmin -> {
                    schoolAdmin.setBlocked(true);
                    schoolAdminRepository.save(schoolAdmin);
                })
                .map(schoolAdmin -> {
                    User user = schoolAdmin.getUser();
                    Role blockedRole = Role.BLOCKED;


                    user.setRole(blockedRole);
                    userRepository.save(user);
                    return mapToSchoolAdminResponse(schoolAdmin);
                })
                .collect(Collectors.toList());

        return responses;
    }

    private SchoolAdminResponse mapToSchoolAdminResponse(SchoolAdmin schoolAdmin) {
        SchoolAdminResponse response = new SchoolAdminResponse();
        response.setEmail(schoolAdmin.getEmail());
        response.setFullName(schoolAdmin.getFullName());

        return response;
    }

    public SchoolAdminResponse findSchoolAdminById(Long schoolAdminId) {
        SchoolAdmin schoolAdmin = schoolAdminRepository.findById(schoolAdminId)
                .orElseThrow(() -> new RuntimeException("Администратор школы не найден"));

        return new SchoolAdminResponse(schoolAdmin.getId(), schoolAdmin.getFullName(), schoolAdmin.getEmail());
    }


    public SchoolAdminResponse look(String schoolAdminEmail) {
        Optional<SchoolAdmin> optionalSchoolAdmin = schoolAdminRepository.findByEmail(schoolAdminEmail);

        if (optionalSchoolAdmin.isPresent()) {
            SchoolAdmin schoolAdmin = optionalSchoolAdmin.get();
            return new SchoolAdminResponse(schoolAdmin.getId(), schoolAdmin.getFullName(), schoolAdmin.getEmail());
        } else {
            return null;
        }
    }

    @Transactional
    public String deleteSchoolAdmins(List<Long> schoolAdminIds) {
        schoolAdminRepository.findAllById(schoolAdminIds)
                .forEach(schoolAdmin -> {
                    schoolAdmin.getRequestSchoolAdmins().forEach(requestSchoolAdmin -> requestSchoolAdmin.setSchoolAdmin(null));
                    User user = schoolAdmin.getUser();
                    if (user != null) {
                        user.setSchoolAdmin(null);
                        userRepository.deleteById(user.getId());
                    }
                });

        schoolAdminRepository.deleteInBatch(schoolAdminRepository.findAllById(schoolAdminIds));

        return "Администраторы школы успешно удалены.";
    }

    public List<BlockedSchoolAdminResponse> getBlockedSchoolAdminsByIds(List<Long> adminIds) {
        List<BlockedSchoolAdminResponse> blockedAdminResponses = schoolAdminRepository.findAllById(adminIds).stream()
                .filter(SchoolAdmin::isBlocked)
                .map(schoolAdmin -> {
                    schoolAdmin.setBlocked(false);
                    schoolAdminRepository.save(schoolAdmin);
                    User user = userRepository.findByEmail(schoolAdmin.getEmail()).orElseThrow();
                    user.setRole(Role.ADMINSCHOOL);
                    userRepository.save(user);
                    BlockedSchoolAdminResponse response = new BlockedSchoolAdminResponse();
                    response.setId(schoolAdmin.getId());
                    response.setFullName(schoolAdmin.getFullName());
                    response.setEmail(schoolAdmin.getEmail());
                    return response;
                })
                .collect(Collectors.toList());

        return blockedAdminResponses;
    }

    public List<SchoolAdminResponse> getAllBlockedSchoolAdmins() {
        List<SchoolAdmin> blockedSchoolAdmins = schoolAdminRepository.findAllBlockedSchoolAdmins();

        return blockedSchoolAdmins.stream()
                .map(schoolAdmin -> new SchoolAdminResponse(schoolAdmin.getId(), schoolAdmin.getFullName(), schoolAdmin.getEmail()))
                .collect(Collectors.toList());
    }

}