package com.challenge.pinapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private int edad;

    public int getEdad() {
        return LocalDate.now().getYear() - fechaNacimiento.getYear();
    }
}
