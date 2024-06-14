package com.challenge.pinapp.service;

import com.challenge.pinapp.dto.ClientDTO;
import com.challenge.pinapp.dto.ClientInfoResponseDTO;
import com.challenge.pinapp.model.Client;
import com.challenge.pinapp.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client createClient(ClientDTO clientDTO) {
        Client client = new Client();
        client.setNombre(clientDTO.getNombre());
        client.setApellido(clientDTO.getApellido());
        client.setFechaNacimiento(clientDTO.getFechaNacimiento());
        client.setEdad(calcularEdad(clientDTO.getFechaNacimiento()));
        return clientRepository.save(client);
    }

    private int calcularEdad(LocalDate fechaNacimiento) {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    @Override
    @Transactional(readOnly = true)
    public double getAverageAge() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .mapToInt(Client::getEdad)
                .average()
                .orElse(0.0);
    }

    @Override
    public double getAgeStandardDeviation() {
        List<Client> clients = clientRepository.findAll();
        double average = getAverageAge();
        double variance = clients.stream()
                .mapToDouble(client -> Math.pow(client.getEdad() - average, 2))
                .average()
                .orElse(0.0);
        return Math.sqrt(variance);
    }

    @Override
    public List<ClientInfoResponseDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ClientInfoResponseDTO convertToDTO(Client client) {
        return new ClientInfoResponseDTO(client.getId(),
                client.getNombre(), client.getApellido(),
                client.getFechaNacimiento(),
                calcularEdad(client.getFechaNacimiento()),
                calculateProbableDeathDate(client));
    }

    private LocalDate calculateProbableDeathDate(Client client) {
        int remainingYears = new Random().nextInt(100 - client.getEdad()) + 1;
        return client.getFechaNacimiento().plusYears(client.getEdad() + remainingYears);
    }
}