package com.stradegi.book_management.controller;

import com.stradegi.book_management.model.dto.RentalDTO;
import com.stradegi.book_management.service.RentalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
@Slf4j
public class RentalController {

    private final RentalService rentalService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<String> rentBook(@RequestBody RentalDTO rentalDTO) {
        log.info("Renting book with details: {}", rentalDTO);
        rentalService.rentBook(rentalDTO);
        log.info("Book rented successfully for book ID: {}", rentalDTO.getBookId());
        return ResponseEntity.ok("Book rented successfully.");
    }

    @PutMapping("/{rentalId}/return")
    public ResponseEntity<String> returnBook(@PathVariable Long rentalId, @RequestParam LocalDate returnDate) {
        try {
            log.info("Returning book for rental ID: {} on return date: {}", rentalId, returnDate);
            rentalService.returnBook(rentalId, returnDate);
            log.info("Book returned successfully for rental ID: {}", rentalId);
            return ResponseEntity.ok("Book returned successfully.");
        } catch (Exception e) {
            log.error("Error returning book for rental ID {}: {}", rentalId, e.getMessage());
            return ResponseEntity.status(500).body("Error returning book!");
        }
    }

    @PutMapping("/{rentalId}/approve")
    public ResponseEntity<String> approveRental(@PathVariable Long rentalId) {
        log.info("Approving rental request for rental ID: {}", rentalId);
        rentalService.approveRental(rentalId);
        log.info("Rental approved successfully for rental ID: {}", rentalId);
        return ResponseEntity.ok("Rental approved successfully.");
    }

    @PutMapping("/{rentalId}/reject")
    public ResponseEntity<String> rejectRental(@PathVariable Long rentalId) {
        log.info("Rejecting rental request for rental ID: {}", rentalId);
        rentalService.rejectRental(rentalId);
        log.info("Rental rejected successfully for rental ID: {}", rentalId);
        return ResponseEntity.ok("Rental rejected successfully.");
    }

    @GetMapping
    public ResponseEntity<List<RentalDTO>> getAllRentals() {
        log.info("Fetching all rental records");
        List<RentalDTO> rentals = rentalService.getAllRentals();
        log.info("Found {} rental records", rentals.size());
        return ResponseEntity.ok(rentals);
    }

    @GetMapping("/{rentalId}")
    public ResponseEntity<RentalDTO> getRentalById(@PathVariable Long rentalId) {
        log.info("Fetching rental record with ID: {}", rentalId);
        RentalDTO rentalDTO = rentalService.getRentalById(rentalId);
        log.info("Rental record fetched: {}", rentalDTO);
        return ResponseEntity.ok(rentalDTO);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<RentalDTO>> getRentalsByMember(@PathVariable Long memberId) {
        log.info("Fetching rental records for member ID: {}", memberId);
        List<RentalDTO> rentalDTOs = rentalService.getRentalsByMember(memberId);
        log.info("Found {} rental records for member ID: {}", rentalDTOs.size(), memberId);
        return ResponseEntity.ok(rentalDTOs);
    }

    @DeleteMapping("/{rentalId}")
    public ResponseEntity<String> deleteRental(@PathVariable Long rentalId) {
        log.info("Deleting rental record with ID: {}", rentalId);
        rentalService.deleteRental(rentalId);
        log.info("Rental record deleted successfully for rental ID: {}", rentalId);
        return ResponseEntity.ok("Rental record deleted successfully.");
    }
}
