package com.rasyid.projectprobation.dto;

import com.rasyid.projectprobation.entity.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "First Name shouldn't be NULL OR EMPTY")
    private String firstname;

    @NotBlank(message = "Last Name shouldn't be NULL OR EMPTY")
    private String lastname;

    @NotBlank(message = "Email shouldn't be NULL OR EMPTY")
    private String email;

    @NotBlank(message = "Password shouldn't be NULL OR EMPTY")
    private String password;

    private Role role;
}
