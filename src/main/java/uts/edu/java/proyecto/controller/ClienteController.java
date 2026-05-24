package uts.edu.java.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uts.edu.java.proyecto.model.Cliente;
import uts.edu.java.proyecto.model.Venta;
import uts.edu.java.proyecto.repository.ClienteRepository;
import uts.edu.java.proyecto.repository.VentaRepository;

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired private ClienteRepository clienteRepository;
    @Autowired private VentaRepository ventaRepository;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("listaClientes", clienteRepository.findAll());
        return "listar-clientes";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "formulario-cliente";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("cliente") Cliente cliente,
                          RedirectAttributes redirectAttrs) {
        clienteRepository.save(cliente);
        redirectAttrs.addFlashAttribute("successMsg", "Cliente guardado correctamente.");
        return "redirect:/clientes/listar";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("cliente", cliente);
        return "formulario-cliente";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id, RedirectAttributes redirectAttrs) {
        // Primero eliminar todas las ventas asociadas al cliente
        List<Venta> ventas = ventaRepository.findAll().stream()
                .filter(v -> v.getCliente().getId().equals(id))
                .toList();
        ventaRepository.deleteAll(ventas);

        // Ahora sí se puede eliminar el cliente
        clienteRepository.deleteById(id);
        redirectAttrs.addFlashAttribute("successMsg", "Cliente y sus ventas eliminados correctamente.");
        return "redirect:/clientes/listar";
    }
}