package com.rasyid.projectprobation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @NotBlank(message = "Email shouldn't be NULL OR EMPTY")
    private String email;

    @NotBlank(message = "Password shouldn't be NULL OR EMPTY")
    String password;
}
