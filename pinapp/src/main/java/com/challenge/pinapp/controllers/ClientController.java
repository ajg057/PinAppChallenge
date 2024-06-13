package com.challenge.pinapp.controllers;

import com.challenge.pinapp.dto.ClientDTO;
import com.challenge.pinapp.model.Client;
import com.challenge.pinapp.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "Create a new Client")
    @ApiResponse(responseCode = "201", description = "Client created successfully",
            content = @Content(schema = @Schema(implementation = ClientDTO.class)))
    @PostMapping("/creacliente")
    public ResponseEntity<Client> createClient(@Valid @RequestBody ClientDTO clientDTO) {
        Client createdClient = clientService.createClient(clientDTO);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }
}
