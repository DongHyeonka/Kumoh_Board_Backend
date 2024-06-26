package com.creativedesignproject.kumoh_board_backend.Auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailCertificationRequestDto {
    @NotBlank
    private String userId;

    @NotBlank
    @Email
    @Pattern(regexp = "^[a-zA-Z0-9]+@kumoh\\.ac\\.kr$")
    private String email;
}