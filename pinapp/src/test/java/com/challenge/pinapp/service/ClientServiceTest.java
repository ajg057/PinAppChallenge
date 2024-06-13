package com.challenge.pinapp.service;

import com.challenge.pinapp.dto.ClientDTO;
import com.challenge.pinapp.model.Client;
import com.challenge.pinapp.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
