package uts.edu.java.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uts.edu.java.proyecto.model.*;
import uts.edu.java.proyecto.repository.*;
import uts.edu.java.proyecto.service.VentaService;

@Controller
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired private ProductoRepository productoRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private VentaRepository ventaRepository;
    @Autowired private VentaService ventaService;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("listaVentas", ventaRepository.findAll());
        return "facturacion";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("listaProductos", productoRepository.findAll());
        model.addAttribute("listaClientes", clienteRepository.findAll());
        model.addAttribute("venta", new Venta());
        return "factura-form";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam("clienteId") Long clienteId,
                          @RequestParam("productoId") Long productoId,
                          @RequestParam("cantidad") Integer cantidad,
                          RedirectAttributes redirectAttrs) {

        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        Producto producto = productoRepository.findById(productoId).orElse(null);

        if (cliente == null || producto == null) {
            redirectAttrs.addFlashAttribute("errorMsg", "Cliente o producto no encontrado.");
            return "redirect:/facturas/nueva";
        }

        // Validar stock ANTES de llamar al servicio para mostrar mensaje amigable
        if (producto.getStock() < cantidad) {
            redirectAttrs.addFlashAttribute("errorMsg",
                "Stock insuficiente para \"" + producto.getNombre() + "\". " +
                "Disponible: " + producto.getStock() + " unidades, solicitaste: " + cantidad + ".");
            return "redirect:/facturas/nueva";
        }

        Venta venta = new Venta();
        venta.setCliente(cliente);

        DetalleVenta detalle = new DetalleVenta();
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setVenta(venta);
        venta.getDetalles().add(detalle);

        ventaService.registrarVenta(venta);

        redirectAttrs.addFlashAttribute("successMsg", "Factura generada correctamente.");
        return "redirect:/facturas/listar";
    }
}