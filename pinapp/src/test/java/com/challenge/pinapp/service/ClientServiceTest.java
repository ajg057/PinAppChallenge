package com.challenge.pinapp.service;

import com.challenge.pinapp.dto.ClientDTO;
import com.challenge.pinapp.model.Client;
import com.challenge.pinapp.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void testCreateClient() {
        // Given
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setNombre("John");
        clientDTO.setApellido("Connor");
        clientDTO.setFechaNacimiento(LocalDate.of(1985, 2, 28));

        Client client = new Client();
        client.setNombre(clientDTO.getNombre());
        client.setApellido(clientDTO.getApellido());
        client.setFechaNacimiento(clientDTO.getFechaNacimiento());
        client.setEdad(client.getEdad());

        // When
        Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);
        Client createdClient = clientService.createClient(clientDTO);

        // Then
        assertNotNull(createdClient);
        assertEquals("John", createdClient.getNombre());
        assertEquals("Connor", createdClient.getApellido());
        assertEquals(39, createdClient.getEdad());
    }

    @Test
    public void testGetAverageAgeAndAgeStandardDeviation() {
        // Given
        Client client1 = new Client();
        client1.setFechaNacimiento(LocalDate.of(1990, 1, 1));

        Client client2 = new Client();
        client2.setFechaNacimiento(LocalDate.of(2000, 1, 1));

        when(clientRepository.findAll()).thenReturn(List.of(client1, client2));

        // When
        double averageAge = clientService.getAverageAge();
        double ageStandardDeviation = clientService.getAgeStandardDeviation();

        // Then
        assertEquals(29.0, averageAge);
        assertEquals(5.0, ageStandardDeviation);
    }
}
