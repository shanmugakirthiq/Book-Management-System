package com.stradegi.book_management.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AdminDashboardDTO {
    private long totalBooks;
    private long booksOnRent;
    private long booksLost;
    private double totalPenalty;
    private long totalMembers;
    private long activeMembers;
    private double totalWalletBalance;
}
