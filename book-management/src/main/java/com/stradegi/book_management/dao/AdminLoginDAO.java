package com.stradegi.book_management.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminLoginDAO {

    private final JdbcTemplate jdbcTemplate;

    public boolean validateAdmin(String username, String password) {
        String sql = "SELECT COUNT(*) FROM admin_login WHERE username = ? AND password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username, password);
        return count != null && count > 0;
    }

    public void insertDefaultAdminIfNotExists() {
        String checkAdmin = "SELECT COUNT(*) FROM admin_login";
        Integer count = jdbcTemplate.queryForObject(checkAdmin, Integer.class);

        if (count != null && count == 0) {
            String insertAdmin = "INSERT INTO admin_login (username, password) VALUES ('admin', 'admin123')";
            jdbcTemplate.update(insertAdmin);
            System.out.println("Default admin user inserted");
        }
    }
}
