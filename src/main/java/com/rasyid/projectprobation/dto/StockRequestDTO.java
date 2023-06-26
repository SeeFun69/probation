package com.rasyid.projectprobation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StockRequestDTO {
    @NotBlank(message = "product name shouldn't be NULL OR EMPTY")
    private String name;

    @Min(value = 1,message = "quantity is not defined !")
    private Long stock;
}
