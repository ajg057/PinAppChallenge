package com.challenge.pinapp.service;

import com.challenge.pinapp.dto.ClientDTO;
import com.challenge.pinapp.model.Client;
import com.challenge.pinapp.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void testCreateCliente() {
        // Given
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setNombre("John");
        clientDTO.setApellido("Connor");
        clientDTO.setFechaNacimiento(LocalDate.of(1985, 2, 28));

        // When
        Client client = clientService.createClient(clientDTO);

        // Then
        assertEquals("John", client.getNombre());
        assertEquals("Connor", client.getApellido());
        assertEquals(39, client.getEdad()); // Assuming current year is 2021
    }

    @Test
    public void testGetAverageAge() {
        // Given
        Client client1 = new Client();
        client1.setFechaNacimiento(LocalDate.of(1990, 1, 1));

        Client client2 = new Client();
        client2.setFechaNacimiento(LocalDate.of(2000, 1, 1));

        when(clientRepository.findAll()).thenReturn(List.of(client1, client2));

        // When
        double averageAge = clientService.getAverageAge();

        // Then
        assertEquals(29.0, averageAge);
    }
}
