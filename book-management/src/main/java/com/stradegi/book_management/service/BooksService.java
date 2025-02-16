package com.stradegi.book_management.service;

import com.stradegi.book_management.model.dto.BooksDTO;

import java.util.List;

public interface BooksService {

    List<BooksDTO> getLowStockBooks();

    void addBooks(BooksDTO bookDTO);

    List<BooksDTO> getAllBooks();

    BooksDTO getBookById(Long bookId);

    boolean updateBooks(Long bookId, BooksDTO updatedBookDTO);

    boolean deleteBooks(Long bookId);
}
