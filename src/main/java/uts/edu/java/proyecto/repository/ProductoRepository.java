package uts.edu.java.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uts.edu.java.proyecto.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}