package com.practice.restapi.dto;

import com.practice.restapi.enums.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookDTO {

    private int bookNo;

    private String title;

    private String author;

    private String isbn;

    private BookStatus status;

    private LocalDate publishedAt;
}
