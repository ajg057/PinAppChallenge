package com.challenge.pinapp.service;

import com.challenge.pinapp.dto.ClientDTO;
import com.challenge.pinapp.model.Client;

public interface ClientService {
    Client createClient(ClientDTO clientDTO);
    double getAverageAge();
    double getAgeStandardDeviation();
}
