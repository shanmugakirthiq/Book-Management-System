package com.stradegi.book_management.dao;

import com.stradegi.book_management.model.entity.Books;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BooksDAO {

    private final JdbcTemplate jdbcTemplate;

    // Add a new book
    public void addBooks(Books book) {
        String query = "INSERT INTO books (name, genre, author, stock, rent_price, published_date, printed_date, edition, date_added) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, book.getName(), book.getGenre(), book.getAuthor(), book.getStock(),
                book.getRentPrice(), Date.valueOf(book.getPublishedDate()), Date.valueOf(book.getPrintedDate()),
                book.getEdition(), Date.valueOf(book.getDateAdded()));
    }

    public List<Books> getAllBooks() {
        String query = "SELECT * FROM books";
        return jdbcTemplate.query(query, (rs, rowNum) -> new Books(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("genre"),
                rs.getString("author"),
                rs.getInt("stock"),
                rs.getDouble("rent_price"),
                rs.getDate("published_date").toLocalDate(),
                rs.getDate("printed_date").toLocalDate(),
                rs.getString("edition"),
                rs.getDate("date_added").toLocalDate()
        ));
    }

    // Get a book by its ID
    public Books getBookById(Long bookId) {
        String query = "SELECT * FROM books WHERE id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{bookId}, (rs, rowNum) -> new Books(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("genre"),
                rs.getString("author"),
                rs.getInt("stock"),
                rs.getDouble("rent_price"),
                rs.getDate("published_date").toLocalDate(),
                rs.getDate("printed_date").toLocalDate(),
                rs.getString("edition"),
                rs.getDate("date_added").toLocalDate()
        ));
    }

    public boolean updateBooks(Long bookId, Books updatedBook) {
        String query = "UPDATE books SET name = ?, genre = ?, author = ?, stock = ?, rent_price = ?, " +
                "published_date = ?, printed_date = ?, edition = ?, date_added = ? WHERE id = ?";
        return jdbcTemplate.update(query, updatedBook.getName(), updatedBook.getGenre(), updatedBook.getAuthor(),
                updatedBook.getStock(), updatedBook.getRentPrice(), Date.valueOf(updatedBook.getPublishedDate()),
                Date.valueOf(updatedBook.getPrintedDate()), updatedBook.getEdition(), Date.valueOf(updatedBook.getDateAdded()),
                bookId) > 0;
    }

    public List<Books> findLowStockBooks(int threshold) {
        String query = "SELECT * FROM books WHERE stock < ?";
        return jdbcTemplate.query(query, new Object[]{threshold}, new BeanPropertyRowMapper<>(Books.class));
    }

    public long countBooks() {
        String query = "SELECT COUNT(*) FROM books";
        return jdbcTemplate.queryForObject(query, Long.class);
    }


    public boolean deleteBooks(Long bookId) {
        String query = "DELETE FROM books WHERE id = ?";
        return jdbcTemplate.update(query, bookId) > 0;
    }
}
