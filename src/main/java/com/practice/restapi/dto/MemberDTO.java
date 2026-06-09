package com.practice.restapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberDTO {

    private int memberNo;

    @NotBlank(message = "회원 ID는 필수입니다.")
    @Size(min = 4, max = 20, message = "회원 ID는 4자 이상 20자 이하입니다.")
    private String id;

    @NotBlank(message = "회원 이름은 필수입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    private LocalDate joinedAt;
}
