package uts.edu.java.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uts.edu.java.proyecto.model.DetalleVenta;

@Repository // <--- ESTA ANOTACIÓN ES CLAVE
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
}