package com.challenge.pinapp.service;

import com.challenge.pinapp.dto.ClientDTO;
import com.challenge.pinapp.dto.ClientInfoResponseDTO;
import com.challenge.pinapp.model.Client;

import java.util.List;

public interface ClientService {
    Client createClient(ClientDTO clientDTO);

    double getAverageAge();

    double getAgeStandardDeviation();

    List<ClientInfoResponseDTO> getAllClients();
}
