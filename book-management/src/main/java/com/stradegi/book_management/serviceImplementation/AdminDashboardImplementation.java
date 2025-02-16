package com.stradegi.book_management.serviceImplementation;

import com.stradegi.book_management.dao.BooksDAO;
import com.stradegi.book_management.dao.MemberDAO;
import com.stradegi.book_management.dao.RentalDAO;
import com.stradegi.book_management.model.dto.AdminDashboardDTO;
import com.stradegi.book_management.service.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDashboardImplementation implements AdminDashboardService {

    private final BooksDAO bookDAO;
    private final RentalDAO rentalDAO;
    private final MemberDAO memberDAO;

    @Override
    public AdminDashboardDTO getDashboardMetrics() {
        long totalBooks = bookDAO.countBooks();
        long booksOnRent = rentalDAO.countBooksOnRent();
        long booksLost = rentalDAO.countBooksLost();
        double totalPenalty = rentalDAO.calculateTotalPenalty();

        long totalMembers = memberDAO.countMembers();
        long activeMembers = memberDAO.countActiveMembers();
        double totalWalletBalance = memberDAO.sumWalletBalance();

        return new AdminDashboardDTO(
                totalBooks,
                booksOnRent,
                booksLost,
                totalPenalty,
                totalMembers,
                activeMembers,
                totalWalletBalance
        );
    }
}
