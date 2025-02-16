package com.stradegi.book_management.service;

import com.stradegi.book_management.model.dto.RentalDTO;
import java.time.LocalDate;
import java.util.List;

public interface RentalService {
    void rentBook(RentalDTO rentalDTO);
    void returnBook(Long rentalId, LocalDate returnDate);
    List<RentalDTO> getAllRentals();
    RentalDTO getRentalById(Long rentalId);
    void deleteRental(Long rentalId);
    double calculatePenalty(LocalDate dueDate, LocalDate returnDate);
    void approveRental(Long rentalId);
    void rejectRental(Long rentalId);
    List<RentalDTO> getRentalsByMember(Long memberId);
}
