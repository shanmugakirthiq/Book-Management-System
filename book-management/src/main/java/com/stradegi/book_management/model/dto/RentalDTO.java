package com.stradegi.book_management.model.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalDTO {
    private Long id;
    private Long memberId;
    private Long bookId;
    private LocalDate rentalDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double penaltyAmount;
    private String status;
}
