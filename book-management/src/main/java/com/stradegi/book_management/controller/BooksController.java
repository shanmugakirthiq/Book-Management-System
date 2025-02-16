package com.stradegi.book_management.controller;

import com.stradegi.book_management.model.dto.BooksDTO;
import com.stradegi.book_management.service.BooksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/books")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class BooksController {

    private final BooksService bookService;

    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestBody BooksDTO bookDTO) {
        bookService.addBooks(bookDTO);
        log.info("New book added: {}", bookDTO.getName());
        return ResponseEntity.ok("Book added successfully!");
    }

    @GetMapping("/all")
    public List<BooksDTO> getAllBooks() {
        log.info("Fetching all books from the database.");
        return bookService.getAllBooks();
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BooksDTO> getBookById(@PathVariable Long bookId) {
        BooksDTO bookDTO = bookService.getBookById(bookId);
        if (bookDTO == null) {
            log.warn("Book with ID {} not found.", bookId);
            return ResponseEntity.notFound().build();
        }
        log.info("Fetching book with ID: {}", bookId);
        return ResponseEntity.ok(bookDTO);
    }

    @PutMapping("/update/{bookId}")
    public ResponseEntity<String> updateBook(@PathVariable Long bookId, @RequestBody BooksDTO updatedBookDTO) {
        boolean isUpdated = bookService.updateBooks(bookId, updatedBookDTO);
        if (isUpdated) {
            log.info("Book with ID {} updated successfully.", bookId);
            return ResponseEntity.ok("Book updated successfully!");
        } else {
            log.warn("Book with ID {} not found for update.", bookId);
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/low-stock")
    public ResponseEntity<List<BooksDTO>> getLowStockBooks() {
        log.info("Fetching books with low stock");
        List<BooksDTO> lowStockBooks = bookService.getLowStockBooks();
        log.info("Found {} books with low stock", lowStockBooks.size());
        return ResponseEntity.ok(lowStockBooks);
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable Long bookId) {
        boolean isDeleted = bookService.deleteBooks(bookId);
        if (isDeleted) {
            log.info("Book with ID {} deleted successfully.", bookId);
            return ResponseEntity.ok("Book deleted successfully!");
        } else {
            log.warn("Book with ID {} not found for deletion.", bookId);
            return ResponseEntity.notFound().build();
        }
    }
}
