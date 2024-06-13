package com.challenge.pinapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientStatisticResponseDTO {
    private double averageAge;
    private double ageStandardDeviation;
}
