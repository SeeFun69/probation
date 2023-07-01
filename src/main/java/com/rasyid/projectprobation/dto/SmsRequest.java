package com.rasyid.projectprobation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsRequest {
    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String message;

    @Override
    public String toString() {
        return "SmsRequest [phoneNumber=" + phoneNumber + ", message=" + message + "]";
    }
}
