package uts.edu.java.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uts.edu.java.proyecto.model.Venta;
import uts.edu.java.proyecto.repository.ProductoRepository;
import uts.edu.java.proyecto.repository.VentaRepository;
import uts.edu.java.proyecto.repository.ClienteRepository;

import java.util.List;

@Controller
public class AppController {

    @Autowired private ProductoRepository productoRepository;
    @Autowired private VentaRepository ventaRepository;
    @Autowired private ClienteRepository clienteRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/inventario")
    public String inventario(Model model) {
        model.addAttribute("listaProductos", productoRepository.findAll());
        return "inventario";
    }

    @GetMapping("/facturacion")
    public String facturacion(Model model) {
        List<Venta> ventas = ventaRepository.findAll();
        double totalIngresos = ventas.stream()
            .mapToDouble(v -> v.getTotal() != null ? v.getTotal() : 0.0)
            .sum();
        model.addAttribute("listaVentas", ventas);
        model.addAttribute("totalIngresos", totalIngresos);
        return "facturacion";
    }
}