package com.challenge.pinapp.controller;

import com.challenge.pinapp.controllers.ClientController;
import com.challenge.pinapp.dto.ClientDTO;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        client.setEdad(41);

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
                .andExpect(jsonPath("$.edad").value(41));
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
}