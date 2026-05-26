package uts.edu.java.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uts.edu.java.proyecto.model.Cliente;
import uts.edu.java.proyecto.repository.ClienteRepository;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        return "listar-cliente";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "formulario-cliente";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {

        model.addAttribute(
                "cliente",
                clienteRepository.findById(id).orElseThrow()
        );

        return "formulario-cliente";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cliente cliente,
                          RedirectAttributes redirectAttributes) {

        try {

            clienteRepository.save(cliente);

            redirectAttributes.addFlashAttribute(
                    "mensaje",
                    "Cliente guardado correctamente"
            );

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "El número de documento ya está registrado para otro cliente"
            );
        }

        return "redirect:/clientes/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id,
                           RedirectAttributes redirectAttributes) {

        try {

            clienteRepository.deleteById(id);

            redirectAttributes.addFlashAttribute(
                    "mensaje",
                    "Cliente eliminado correctamente"
            );

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "No se puede eliminar este cliente porque tiene ventas asociadas"
            );
        }

        return "redirect:/clientes/listar";
    }
}