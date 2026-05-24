package uts.edu.java.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uts.edu.java.proyecto.model.Venta;
import uts.edu.java.proyecto.repository.*;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired private ClienteRepository clienteRepo;
    @Autowired private ProductoRepository productoRepo;
    @Autowired private VentaRepository ventaRepo;
    @Autowired private UsuarioRepository usuarioRepo;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        List<Venta> ventas = ventaRepo.findAll();

        // ===== ESTADÍSTICAS =====
        model.addAttribute("totalClientes", clienteRepo.count());
        model.addAttribute("totalProductos", productoRepo.count());
        model.addAttribute("totalUsuarios", usuarioRepo.count());

        double totalVentas = ventas.stream()
                .mapToDouble(v -> v.getTotal() != null ? v.getTotal() : 0.0)
                .sum();

        model.addAttribute("totalVentas", totalVentas);

        // ===== VENTAS =====
        model.addAttribute("ultimasVentas",
                ventas.stream()
                        .sorted((a, b) -> b.getFecha().compareTo(a.getFecha()))
                        .limit(10)
                        .toList()
        );

        // 🔥 IMPORTANTE: SIEMPRE NUEVA LISTA (EVITA CACHE / NULL / REFRESH BUG)
        model.addAttribute("productos", productoRepo.findAll());

        return "dashboard";
    }
}