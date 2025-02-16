package com.stradegi.book_management.service;

import com.stradegi.book_management.model.dto.AdminLoginDTO;

public interface AdminLoginService {
    boolean validateAdmin(String username, String password);
    AdminLoginDTO getAdminByUsername(String username);
}
