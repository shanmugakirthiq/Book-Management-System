package com.stradegi.book_management.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BooksDTO {

    private String name;
    private String genre;
    private String author;
    private int stock;
    private double rentPrice;
    private LocalDate publishedDate;
    private LocalDate printedDate;
    private String edition;
    private LocalDate dateAdded;

}
