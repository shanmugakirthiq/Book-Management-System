package com.stradegi.book_management.serviceImplementation;

import com.stradegi.book_management.dao.AdminLoginDAO;
import com.stradegi.book_management.model.dto.AdminLoginDTO;
import com.stradegi.book_management.service.AdminLoginService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminLoginServiceImplementation implements AdminLoginService {

    private final AdminLoginDAO adminLoginDAO;

    @PostConstruct
    public void initializeAdmin() {
        log.info("Checking if default admin user exists...");
        adminLoginDAO.insertDefaultAdminIfNotExists();
    }

    @Override
    public boolean validateAdmin(String username, String password) {
        return adminLoginDAO.validateAdmin(username, password);
    }

    @Override
    public AdminLoginDTO getAdminByUsername(String username) {
        log.info("Fetching admin details for username: {}", username);
        return new AdminLoginDTO("admin");
    }
}
