package com.stradegi.book_management.dao;

import com.stradegi.book_management.model.entity.Rental;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public class RentalDAO {

    private final JdbcTemplate jdbcTemplate;

    public RentalDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Rental> getRentalsByMember(Long memberId) {
        String query = "SELECT * FROM rentals WHERE member_id = ?";
        return jdbcTemplate.query(query, new Object[]{memberId}, (rs, rowNum) -> new Rental(
                rs.getLong("id"),
                null, // In a complete implementation, join to fetch the Member entity
                null, // In a complete implementation, join to fetch the Book entity
                rs.getObject("rental_date", LocalDate.class),
                rs.getObject("due_date", LocalDate.class),
                rs.getObject("return_date", LocalDate.class),
                rs.getDouble("penalty_amount"),
                rs.getString("status")
        ));
    }

    public void addRental(Rental rental) {
        String query = "INSERT INTO rentals (member_id, book_id, rental_date, due_date, status) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(query,
                rental.getMember().getMemberId(),
                rental.getBook().getId(),
                rental.getRentalDate(),
                rental.getDueDate(),
                rental.getStatus());
    }

    public List<Rental> getAllRentals() {
        String query = "SELECT * FROM rentals";
        return jdbcTemplate.query(query, (rs, rowNum) -> new Rental(
                rs.getLong("id"),
                null, // In a full implementation, join to fetch the Member entity
                null, // In a full implementation, join to fetch the Book entity
                rs.getObject("rental_date", LocalDate.class),
                rs.getObject("due_date", LocalDate.class),
                rs.getObject("return_date", LocalDate.class),
                rs.getDouble("penalty_amount"),
                rs.getString("status")
        ));
    }

    public Rental getRentalById(Long rentalId) {
        String query = "SELECT * FROM rentals WHERE id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{rentalId}, (rs, rowNum) -> new Rental(
                rs.getLong("id"),
                null,
                null,
                rs.getObject("rental_date", LocalDate.class),
                rs.getObject("due_date", LocalDate.class),
                rs.getObject("return_date", LocalDate.class),
                rs.getDouble("penalty_amount"),
                rs.getString("status")
        ));
    }

    public void updateRental(Long rentalId, Rental rental) {
        String query = "UPDATE rentals SET return_date = ?, penalty_amount = ? WHERE id = ?";
        jdbcTemplate.update(query, rental.getReturnDate(), rental.getPenaltyAmount(), rentalId);
    }

    public void updateRentalStatus(Long rentalId, Rental rental) {
        String query = "UPDATE rentals SET status = ? WHERE id = ?";
        jdbcTemplate.update(query, rental.getStatus(), rentalId);
    }

    public void deleteRental(Long rentalId) {
        String query = "DELETE FROM rentals WHERE id = ?";
        jdbcTemplate.update(query, rentalId);
    }

public long countBooksOnRent() {
        String query = "SELECT COUNT(*) FROM rentals WHERE return_date IS NULL";
        return jdbcTemplate.queryForObject(query, Long.class);
    }

    public long countBooksLost() {
        String query = "SELECT COUNT(*) FROM rentals WHERE status = 'LOST'";
        return jdbcTemplate.queryForObject(query, Long.class);
    }

    public double calculateTotalPenalty() {
        String query = "SELECT COALESCE(SUM(penalty), 0) FROM rentals WHERE return_date IS NOT NULL";
        return jdbcTemplate.queryForObject(query, Double.class);
    }

}
