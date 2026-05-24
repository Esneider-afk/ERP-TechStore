package uts.edu.java.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uts.edu.java.proyecto.model.Usuario;
import uts.edu.java.proyecto.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("listaUsuarios", usuarioRepository.findAll());
        return "listar-usuarios";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("esNuevo", true);
        return "formulario-usuario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("usuario") Usuario usuario,
                          @RequestParam(value = "passwordNuevo", required = false) String passwordNuevo,
                          RedirectAttributes redirectAttrs) {

        if (usuario.getId() == null) {
            if (passwordNuevo == null || passwordNuevo.isBlank()) {
                redirectAttrs.addFlashAttribute("errorMsg", "La contraseña es obligatoria.");
                return "redirect:/usuarios/nuevo";
            }
            usuario.setPassword(passwordEncoder.encode(passwordNuevo));
        } else {
            if (passwordNuevo != null && !passwordNuevo.isBlank()) {
                usuario.setPassword(passwordEncoder.encode(passwordNuevo));
            } else {
                Usuario existente = usuarioRepository.findById(usuario.getId()).orElseThrow();
                usuario.setPassword(existente.getPassword());
            }
        }

        String rol = usuario.getRol();
        if (rol != null && !rol.startsWith("ROLE_")) {
            usuario.setRol("ROLE_" + rol);
        }

        usuarioRepository.save(usuario);
        redirectAttrs.addFlashAttribute("successMsg", "Usuario guardado correctamente.");
        return "redirect:/usuarios/listar";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        usuario.setPassword("");
        model.addAttribute("usuario", usuario);
        model.addAttribute("esNuevo", false);
        return "formulario-usuario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id, RedirectAttributes redirectAttrs) {
        usuarioRepository.deleteById(id);
        redirectAttrs.addFlashAttribute("successMsg", "Usuario eliminado correctamente.");
        return "redirect:/usuarios/listar";
    }
}