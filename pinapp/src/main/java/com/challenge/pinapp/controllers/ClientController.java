package com.challenge.pinapp.controllers;

import com.challenge.pinapp.dto.AverageAgeResponse;
import com.challenge.pinapp.dto.ClientDTO;
import com.challenge.pinapp.model.Client;
import com.challenge.pinapp.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Operation(summary = "Get average age of clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved average age"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/kpideclientes")
    public ResponseEntity<AverageAgeResponse> getAverageAge() {
        double averageAge = clientService.getAverageAge();
        AverageAgeResponse response = new AverageAgeResponse(averageAge);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
