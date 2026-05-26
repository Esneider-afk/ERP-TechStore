package uts.edu.java.proyecto.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import uts.edu.java.proyecto.model.Cliente;
import uts.edu.java.proyecto.model.DetalleVenta;
import uts.edu.java.proyecto.model.Producto;
import uts.edu.java.proyecto.model.Venta;

import uts.edu.java.proyecto.repository.ClienteRepository;
import uts.edu.java.proyecto.repository.ProductoRepository;
import uts.edu.java.proyecto.repository.VentaRepository;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/listar")
    public String listar(Model model) {

        List<Venta> ventas = ventaRepository.findAll();

        double totalIngresos = ventas.stream()
                .mapToDouble(v -> v.getTotal() != null ? v.getTotal() : 0.0)
                .sum();

        model.addAttribute("listaVentas", ventas);
        model.addAttribute("totalIngresos", totalIngresos);

        return "facturacion";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {

        model.addAttribute("listaProductos", productoRepository.findAll());
        model.addAttribute("listaClientes", clienteRepository.findAll());

        return "factura-form";
    }

    @PostMapping("/guardar")
    public String guardarVenta(

            @RequestParam("clienteId") Long clienteId,

            @RequestParam(value = "productoId", required = false)
            List<Long> productosIds,

            @RequestParam(value = "cantidad", required = false)
            List<Integer> cantidades,

            RedirectAttributes redirectAttrs) {

        // VALIDAR PRODUCTOS
        if (productosIds == null || productosIds.isEmpty()) {

            redirectAttrs.addFlashAttribute(
                    "errorMsg",
                    "Debes agregar al menos un producto antes de generar la factura.");

            return "redirect:/ventas/nueva";
        }

        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);

        if (cliente == null) {

            redirectAttrs.addFlashAttribute(
                    "errorMsg",
                    "Cliente no encontrado.");

            return "redirect:/ventas/nueva";
        }

        Venta venta = new Venta();

        venta.setCliente(cliente);
        venta.setFecha(LocalDateTime.now());

        List<DetalleVenta> detalles = new ArrayList<>();

        double total = 0.0;

        for (int i = 0; i < productosIds.size(); i++) {

            Long productoId = productosIds.get(i);

            Integer cantidad = cantidades.get(i);

            if (cantidad == null || cantidad <= 0) {
                continue;
            }

            Producto producto = productoRepository.findById(productoId).orElse(null);

            if (producto == null) {
                continue;
            }

            // VALIDAR STOCK
            if (producto.getStock() < cantidad) {

                redirectAttrs.addFlashAttribute(
                        "errorMsg",
                        "Stock insuficiente para el producto: "
                                + producto.getNombre());

                return "redirect:/ventas/nueva";
            }

            double subtotal = producto.getPrecio() * cantidad;

            DetalleVenta detalle = new DetalleVenta();

            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(producto.getPrecio());

            detalles.add(detalle);

            total += subtotal;

            // ACTUALIZAR STOCK
            producto.setStock(producto.getStock() - cantidad);

            productoRepository.save(producto);
        }

        // VALIDAR SI NO AGREGÓ NADA
        if (detalles.isEmpty()) {

            redirectAttrs.addFlashAttribute(
                    "errorMsg",
                    "Debes agregar productos válidos.");

            return "redirect:/ventas/nueva";
        }

        venta.setDetalles(detalles);
        venta.setTotal(total);

        // GUARDAR VENTA Y DETALLES AUTOMÁTICAMENTE
        ventaRepository.save(venta);

        redirectAttrs.addFlashAttribute(
                "successMsg",
                "Factura generada correctamente.");

        return "redirect:/ventas/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id,
                           RedirectAttributes redirectAttrs) {

        Venta venta = ventaRepository.findById(id).orElse(null);

        if (venta == null) {

            redirectAttrs.addFlashAttribute(
                "errorMsg",
                "La venta no existe.");

            return "redirect:/ventas/listar";
        }

        try {

            // RESTAURAR STOCK
            for (DetalleVenta detalle : venta.getDetalles()) {

                Producto producto = detalle.getProducto();

                if (producto != null) {

                    int nuevoStock =
                        producto.getStock() + detalle.getCantidad();

                    producto.setStock(nuevoStock);

                    productoRepository.save(producto);
                }
            }

            // ELIMINAR VENTA
            // orphanRemoval + cascade eliminan detalles automáticamente
            ventaRepository.delete(venta);

            redirectAttrs.addFlashAttribute(
                "successMsg",
                "Factura eliminada correctamente.");

        } catch (Exception e) {

            e.printStackTrace();

            redirectAttrs.addFlashAttribute(
                "errorMsg",
                "Error al eliminar la factura.");
        }

        return "redirect:/ventas/listar";
    }
}