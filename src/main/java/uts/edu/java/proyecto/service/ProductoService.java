package uts.edu.java.proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uts.edu.java.proyecto.model.Producto;
import uts.edu.java.proyecto.repository.ProductoRepository;
import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarTodos() { return productoRepository.findAll(); }
    public Producto guardar(Producto p) { return productoRepository.save(p); }
    public Producto obtenerPorId(Long id) { return productoRepository.findById(id).orElse(null); }
    public void eliminar(Long id) { productoRepository.deleteById(id); }
}