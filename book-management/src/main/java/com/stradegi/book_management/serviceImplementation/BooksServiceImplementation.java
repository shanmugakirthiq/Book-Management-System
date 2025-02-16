package com.stradegi.book_management.serviceImplementation;

import com.stradegi.book_management.dao.BooksDAO;
import com.stradegi.book_management.model.dto.BooksDTO;
import com.stradegi.book_management.model.entity.Books;
import com.stradegi.book_management.service.BooksService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BooksServiceImplementation implements BooksService {

    private final BooksDAO bookDAO;
    private final ModelMapper modelMapper;

    @Override
    public void addBooks(BooksDTO bookDTO) {
        Books book = modelMapper.map(bookDTO, Books.class);
        bookDAO.addBooks(book);
    }

    @Override
    public List<BooksDTO> getAllBooks() {
        List<Books> books = bookDAO.getAllBooks();
        return books.stream()
                .map(book -> modelMapper.map(book, BooksDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public BooksDTO getBookById(Long bookId) {
        Books book = bookDAO.getBookById(bookId);
        return modelMapper.map(book, BooksDTO.class);
    }

    @Override
    public boolean updateBooks(Long bookId, BooksDTO updatedBookDTO) {
        Books updatedBook = modelMapper.map(updatedBookDTO, Books.class);
        return bookDAO.updateBooks(bookId, updatedBook);
    }

    @Override
    public List<BooksDTO> getLowStockBooks() {
        int threshold = 3;
        List<Books> lowStockBooks = bookDAO.findLowStockBooks(threshold);
        return lowStockBooks.stream()
                .map(book -> modelMapper.map(book, BooksDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteBooks(Long bookId) {
        return bookDAO.deleteBooks(bookId);
    }
}
