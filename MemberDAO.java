package com.stradegi.book_management.dao;

import com.stradegi.book_management.model.dto.MemberDTO;
import com.stradegi.book_management.model.dto.SubscriptionPlanDTO;
import com.stradegi.book_management.model.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Repository
public class MemberDAO {

    private final JdbcTemplate jdbcTemplate;

    public MemberDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Modified RowMapper to correctly instantiate SubscriptionPlanDTO using the no-arg constructor and setter.
    private final RowMapper<MemberDTO> memberRowMapper = (rs, rowNum) -> {
        Long subscriptionId = rs.getObject("subscription_plan_id", Long.class);
        SubscriptionPlanDTO subscriptionPlanDTO = null;
        if (subscriptionId != null) {
            subscriptionPlanDTO = new SubscriptionPlanDTO();
            subscriptionPlanDTO.setSubscriptionId(subscriptionId);
        }
        return new MemberDTO(
                rs.getLong("member_id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("mobile_number"),
                rs.getString("gender"),
                rs.getBoolean("is_active"),
                rs.getDouble("wallet_balance"),
                subscriptionPlanDTO
        );
    };

    public void registerMember(Member member) {
        // Ensure subscription plan is provided and not null
        if (member.getSubscriptionPlan() == null || member.getSubscriptionPlan().getSubscriptionId() == null) {
            throw new IllegalArgumentException("Subscription plan must be selected.");
        }

        String sql = "INSERT INTO members (name, email, password, mobile_number, gender, subscription_plan_id, is_active, wallet_balance, first_login) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Long subscriptionId = member.getSubscriptionPlan().getSubscriptionId();

        jdbcTemplate.update(sql,
                member.getName(),
                member.getEmail(),
                member.getPassword(),
                member.getMobileNumber(),
                member.getGender(),
                subscriptionId,
                false,
                0.0,
                true
        );
        log.info("New member registered: {}", member.getEmail());
    }

    public Long getMemberIdByEmail(String email) {
        String sql = "SELECT member_id FROM members WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, email);
    }

    public void approveMember(Long memberId, Long subscriptionId, double price, double commission, int durationDays) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(durationDays);

        String sql = "UPDATE members SET " +
                "password = CONCAT(name, '123@A'), " +
                "is_active = ?, " +
                "wallet_balance = wallet_balance + ? - ?, " +
                "subscription_start_date = ?, " +
                "subscription_end_date = ? " +
                "WHERE member_id = ?";

        jdbcTemplate.update(sql, true, price, commission, startDate, endDate, memberId);
        log.info("Member {} approved. Subscription ends on {}", memberId, endDate);
    }

    public long countMembers() {
        String query = "SELECT COUNT(*) FROM members";
        return jdbcTemplate.queryForObject(query, Long.class);
    }

    public long countActiveMembers() {
        String query = "SELECT COUNT(*) FROM members WHERE is_active = true";
        return jdbcTemplate.queryForObject(query, Long.class);
    }

    public double sumWalletBalance() {
        String query = "SELECT COALESCE(SUM(wallet_balance), 0) FROM members";
        return jdbcTemplate.queryForObject(query, Double.class);
    }

    public boolean isMemberActive(Long memberId) {
        String sql = "SELECT is_active FROM members WHERE member_id = ?";
        Boolean isActive = jdbcTemplate.queryForObject(sql, Boolean.class, memberId);
        return isActive != null && isActive;
    }

    public List<MemberDTO> getAllMembers(int page, int size) {
        int offset = page * size;
        String sql = "SELECT member_id, name, email, mobile_number, gender, is_active, wallet_balance, subscription_plan_id FROM members LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, memberRowMapper, size, offset);
    }

    public List<MemberDTO> getPendingMembers() {
        String sql = "SELECT member_id, name, email, mobile_number, gender, is_active, wallet_balance, subscription_plan_id FROM members WHERE is_active = false";
        return jdbcTemplate.query(sql, memberRowMapper);
    }

    public void deleteMember(Long memberId) {
        String sql = "DELETE FROM members WHERE member_id = ?";
        jdbcTemplate.update(sql, memberId);
        log.info("Member with ID: {} deleted from the database.", memberId);
        resetAutoIncrementForMembers();
    }

    private void resetAutoIncrementForMembers() {
        String checkQuery = "SELECT COUNT(*) FROM members";
        Integer count = jdbcTemplate.queryForObject(checkQuery, Integer.class);
        if (count != null && count == 0) {
            String resetSql = "ALTER TABLE members AUTO_INCREMENT = 1";
            jdbcTemplate.update(resetSql);
        }
    }

    public boolean checkCredentials(String email, String password) {
        String sql = "SELECT COUNT(*) FROM members WHERE email = ? AND password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email, password);
        return count != null && count > 0;
    }

    public void updateMemberPassword(Long memberId, String newPassword) {
        String sql = "UPDATE members SET password = ?, first_login = false WHERE member_id = ?";
        jdbcTemplate.update(sql, newPassword, memberId);
    }

    public boolean isFirstLogin(String email) {
        String sql = "SELECT first_login FROM members WHERE email = ?";
        Boolean firstLogin = jdbcTemplate.queryForObject(sql, Boolean.class, email);
        return firstLogin != null && firstLogin;
    }

    public void updateMember(Member member) {
        String sql = "UPDATE members SET name = ?, email = ?, mobile_number = ?, gender = ?, wallet_balance = ?, subscription_plan_id = ?, is_active = ? WHERE member_id = ?";
        Long subscriptionId = (member.getSubscriptionPlan() != null) ? member.getSubscriptionPlan().getSubscriptionId() : null;
        jdbcTemplate.update(sql,
                member.getName(),
                member.getEmail(),
                member.getMobileNumber(),
                member.getGender(),
                member.getWalletBalance(),
                subscriptionId,
                member.isActive(),
                member.getMemberId());
        log.info("Updated member: {}", member.getMemberId());
    }
}
