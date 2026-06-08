package com.practice.restapi.dto;

import com.practice.restapi.enums.RentalStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RentalDTO {

    private int rentalNo;

    private int memberNo;

    private int bookNo;

    private LocalDate rentedAt;

    private LocalDate dueDate;

    private LocalDate returnedAt;

    private RentalStatus status;
}
