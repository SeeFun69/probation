package com.rasyid.projectprobation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StockDTO {
    @NotBlank(message = "stock name shouldn't be NULL OR EMPTY")
    private String name;

    @Min(value = 1,message = "quantity is not defined !")
    @NotNull(message = "quantity can not null")
    private Integer stock;
}
