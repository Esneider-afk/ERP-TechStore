package uts.edu.java.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uts.edu.java.proyecto.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}