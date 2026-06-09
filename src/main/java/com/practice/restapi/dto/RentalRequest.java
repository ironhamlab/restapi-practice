package com.practice.restapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RentalRequest {

    @NotNull(message = "회원 번호는 필수입니다.")
    private Integer memberNo;

    @NotNull(message = "도서 번호는 필수입니다.")
    private Integer bookNo;
}
