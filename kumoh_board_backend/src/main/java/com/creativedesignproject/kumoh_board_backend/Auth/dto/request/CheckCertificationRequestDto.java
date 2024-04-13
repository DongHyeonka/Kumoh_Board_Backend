package com.creativedesignproject.kumoh_board_backend.Auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckCertificationRequestDto {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String certificationNumber;
}
