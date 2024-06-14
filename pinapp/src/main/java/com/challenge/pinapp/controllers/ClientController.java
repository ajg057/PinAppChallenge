package com.challenge.pinapp.controllers;

import com.challenge.pinapp.dto.ClientDTO;
import com.challenge.pinapp.dto.ClientInfoResponseDTO;
import com.challenge.pinapp.dto.ClientStatisticResponseDTO;
import com.challenge.pinapp.model.Client;
import com.challenge.pinapp.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

import java.util.List;

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

    @Operation(summary = "Get average age and standard deviation of ages of clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved average age and standard deviation"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/kpideclientes")
    public ResponseEntity<ClientStatisticResponseDTO> getClientStatistics() {
        double averageAge = clientService.getAverageAge();
        double standardDeviation = clientService.getAgeStandardDeviation();

        ClientStatisticResponseDTO response = new ClientStatisticResponseDTO(averageAge, standardDeviation);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get all client information", description = "Returns clients with its data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The clients were not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ClientInfoResponseDTO.class)))
                    })
    })
    @GetMapping("/listclients")
    public ResponseEntity<List<ClientInfoResponseDTO>> getAllClients() {
        List<ClientInfoResponseDTO> clients = clientService.getAllClients();
        if (clients.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }
}
