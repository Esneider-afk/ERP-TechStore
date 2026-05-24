package uts.edu.java.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uts.edu.java.proyecto.model.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {
}