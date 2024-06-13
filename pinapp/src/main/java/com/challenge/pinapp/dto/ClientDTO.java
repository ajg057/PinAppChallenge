package com.challenge.pinapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientDTO {

    @Schema(name = "Nombre", example = "John", required = true)
    @NotBlank(message = "Nombre is mandatory")
    @Size(min = 2, max = 30, message = "Nombre must be between 2 and 30 characters")
    private String nombre;

    @Schema(name = "Apellido", example = "Doe", required = true)
    @NotBlank(message = "Apellido is mandatory")
    @Size(min = 2, max = 50, message = "Apellido must be between 2 and 50 characters")
    private String apellido;

    @Schema(name = "Fecha de nacimiento", example = "2020-02-04", required = true)
    @NotNull(message = "Fecha de nacimiento is mandatory")
    @Past(message = "Fecha de nacimiento must be in the past")
    private LocalDate fechaNacimiento;
}
