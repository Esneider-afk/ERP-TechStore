package uts.edu.java.proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uts.edu.java.proyecto.model.Cliente;
import uts.edu.java.proyecto.repository.ClienteRepository;
import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() { return clienteRepository.findAll(); }
    public Cliente guardar(Cliente c) { return clienteRepository.save(c); }
}