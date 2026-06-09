package com.practice.restapi.dto;

import com.practice.restapi.enums.BookStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
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

    @NotBlank(message = "도서 제목은 필수입니다.")
    private String title;

    @NotBlank(message = "저자는 필수입니다.")
    private String author;

    @NotBlank(message = "ISBN은 필수입니다.")
    private String isbn;

    private BookStatus status;

    @PastOrPresent(message = "출판일은 오늘 또는 과거 날짜여야 합니다.")
    private LocalDate publishedAt;
}
