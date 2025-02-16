package com.stradegi.book_management.serviceImplementation;

import com.stradegi.book_management.dao.RentalDAO;
import com.stradegi.book_management.model.dto.RentalDTO;
import com.stradegi.book_management.model.entity.Rental;
import com.stradegi.book_management.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalServiceImplementation implements RentalService {

    private final RentalDAO rentalDAO;
    private final ModelMapper modelMapper;


    @Override
    public double calculatePenalty(LocalDate dueDate, LocalDate returnDate) {
        if (returnDate == null || returnDate.isBefore(dueDate)) {
            return 0.0;
        }
        long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
        return daysLate * 10.0;
    }

    @Override
    public void rentBook(RentalDTO rentalDTO) {
        // Map RentalDTO to Rental entity
        Rental rental = modelMapper.map(rentalDTO, Rental.class);
        // Set default status for new rental request
        rental.setStatus("PENDING");
        rentalDAO.addRental(rental);
    }

    @Override
    public void returnBook(Long rentalId, LocalDate returnDate) {
        Rental rental = rentalDAO.getRentalById(rentalId);
        double penalty = calculatePenalty(rental.getDueDate(), returnDate);
        rental.setReturnDate(returnDate);
        rental.setPenaltyAmount(penalty);
        rentalDAO.updateRental(rentalId, rental);
    }

    @Override
    public List<RentalDTO> getAllRentals() {
        List<Rental> rentals = rentalDAO.getAllRentals();
        return rentals.stream()
                .map(rental -> modelMapper.map(rental, RentalDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RentalDTO getRentalById(Long rentalId) {
        Rental rental = rentalDAO.getRentalById(rentalId);
        return modelMapper.map(rental, RentalDTO.class);
    }

    @Override
    public void deleteRental(Long rentalId) {
        rentalDAO.deleteRental(rentalId);
    }

    @Override
    public void approveRental(Long rentalId) {
        Rental rental = rentalDAO.getRentalById(rentalId);
        rental.setStatus("APPROVED");
        rentalDAO.updateRentalStatus(rentalId, rental);
    }

    @Override
    public void rejectRental(Long rentalId) {
        Rental rental = rentalDAO.getRentalById(rentalId);
        rental.setStatus("REJECTED");
        rentalDAO.updateRentalStatus(rentalId, rental);
    }

    @Override
    public List<RentalDTO> getRentalsByMember(Long memberId) {
        List<Rental> rentals = rentalDAO.getRentalsByMember(memberId);
        return rentals.stream()
                .map(rental -> modelMapper.map(rental, RentalDTO.class))
                .collect(Collectors.toList());
    }
}
