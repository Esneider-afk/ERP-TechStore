package uts.edu.java.proyecto.model;

import jakarta.persistence.*;
import java.time.LocalDate;
// IMPORTANTE: Asegúrate de añadir esta importación arriba
import org.springframework.format.annotation.DateTimeFormat; 

@Entity
@Table(name = "facturas")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String cliente;
    private Double total;

    // AQUÍ ES DONDE LO AGREGAS
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fecha;

    public Factura() { this.fecha = LocalDate.now(); }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}