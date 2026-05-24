package uts.edu.java.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uts.edu.java.proyecto.model.Factura;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
}