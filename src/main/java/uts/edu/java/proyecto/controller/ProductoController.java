package uts.edu.java.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uts.edu.java.proyecto.model.Producto;
import uts.edu.java.proyecto.repository.ProductoRepository;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired private ProductoRepository prodRepo;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("listaProductos", prodRepo.findAll());
        return "inventario";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("esNuevo", true);
        return "producto-form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto,
                          RedirectAttributes redirectAttrs) {
        prodRepo.save(producto);
        redirectAttrs.addFlashAttribute("successMsg", "Producto guardado correctamente.");
        return "redirect:/productos/listar";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {
        Producto producto = prodRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("producto", producto);
        model.addAttribute("esNuevo", false);
        return "producto-form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id, RedirectAttributes redirectAttrs) {
        prodRepo.deleteById(id);
        redirectAttrs.addFlashAttribute("successMsg", "Producto eliminado correctamente.");
        return "redirect:/productos/listar";
    }
}