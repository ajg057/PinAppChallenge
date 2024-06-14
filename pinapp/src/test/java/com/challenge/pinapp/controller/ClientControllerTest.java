package com.challenge.pinapp.controller;

import com.challenge.pinapp.controllers.ClientController;
import com.challenge.pinapp.dto.ClientDTO;
import com.challenge.pinapp.dto.ClientInfoResponseDTO;
import com.challenge.pinapp.model.Client;
import com.challenge.pinapp.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
@AutoConfigureMockMvc
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;

    @Test
    public void testGivenClientWhenCreateValidInputThenSuccess() throws Exception {
        // Given
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setNombre("John");
        clientDTO.setApellido("Doe");
        clientDTO.setFechaNacimiento(LocalDate.of(1980, 5, 15));

        Client client = new Client();
        client.setNombre(clientDTO.getNombre());
        client.setApellido(clientDTO.getApellido());
        client.setFechaNacimiento(clientDTO.getFechaNacimiento());
        client.setEdad(44);

        when(clientService.createClient(any(ClientDTO.class))).thenReturn(client);

        // When
        ResultActions resultActions = mockMvc.perform(post("/creacliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)));

        // Then
        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").value("John"))
                .andExpect(jsonPath("$.apellido").value("Doe"))
                .andExpect(jsonPath("$.edad").value(44));
    }

    @Test
    public void testGivenClientWhenCreateInvalidValidInputThenError() throws Exception {
        // Given
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setNombre("John");
        clientDTO.setApellido("Doe");
        clientDTO.setFechaNacimiento(LocalDate.now().plusDays(1));

        // When
        ResultActions resultActions = mockMvc.perform(post("/creacliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)));

        // Then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void testGivenClientsThenGetAverageAge() throws Exception {
        // Given
        when(clientService.getAverageAge()).thenReturn(25.0);

        // When
        ResultActions resultActions = mockMvc.perform(get("/kpideclientes")
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.averageAge").value(25.0));
    }

    @Test
    public void testGetClientStatistics() throws Exception {
        // Given
        when(clientService.getAverageAge()).thenReturn(30.5);
        when(clientService.getAgeStandardDeviation()).thenReturn(5.0);

        // When
        ResultActions resultActions = mockMvc.perform(get("/kpideclientes")
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.averageAge").value(30.5))
                .andExpect(jsonPath("$.ageStandardDeviation").value(5.0));
    }

    @Test
    public void testGetAllClients() throws Exception {
        // Given
        ClientInfoResponseDTO client1 = new ClientInfoResponseDTO(1L,
                "John",
                "Doe",
                LocalDate.of(1985, 5, 15),
                36,
                LocalDate.of(2077, 1, 1));

        ClientInfoResponseDTO client2 = new ClientInfoResponseDTO(
                2L,
                "Jane",
                "Smith",
                LocalDate.of(1990, 8, 25),
                31,
                LocalDate.of(2067, 1, 1));
        List<ClientInfoResponseDTO> clientList = Arrays.asList(client1, client2);

        when(clientService.getAllClients()).thenReturn(clientList);

        // When
        ResultActions resultActions = mockMvc.perform(get("/listclients")
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(clientList.size()))
                .andExpect(jsonPath("$[0].id").value(client1.getId()))
                .andExpect(jsonPath("$[0].nombre").value(client1.getNombre()))
                .andExpect(jsonPath("$[0].apellido").value(client1.getApellido()))
                .andExpect(jsonPath("$[0].edad").value(client1.getEdad()))
                .andExpect(jsonPath("$[0].probableFechaMuerte").value(client1.getProbableFechaMuerte().toString()))
                .andExpect(jsonPath("$[1].id").value(client2.getId()))
                .andExpect(jsonPath("$[1].nombre").value(client2.getNombre()))
                .andExpect(jsonPath("$[1].apellido").value(client2.getApellido()))
                .andExpect(jsonPath("$[1].edad").value(client2.getEdad()))
                .andExpect(jsonPath("$[1].probableFechaMuerte").value(client2.getProbableFechaMuerte().toString()));
    }

    @Test
    public void testGetAllClientsWhenEmptyList() throws Exception {
        // Given
        when(clientService.getAllClients()).thenReturn(Collections.emptyList());

        // When
        ResultActions resultActions = mockMvc.perform(get("/listclients")
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isNotFound());
    }

}