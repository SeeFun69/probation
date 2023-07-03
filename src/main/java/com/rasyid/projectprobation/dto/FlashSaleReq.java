package com.rasyid.projectprobation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FlashSaleReq {
//    @NotBlank(message = "stock name shouldn't be NULL OR EMPTY")
//    private String username;

    @NotBlank(message = "stock name shouldn't be NULL OR EMPTY")
    private String stockname;
}
