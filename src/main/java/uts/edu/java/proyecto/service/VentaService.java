package uts.edu.java.proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uts.edu.java.proyecto.model.DetalleVenta;
import uts.edu.java.proyecto.model.Producto;
import uts.edu.java.proyecto.model.Venta;
import uts.edu.java.proyecto.repository.ProductoRepository;
import uts.edu.java.proyecto.repository.VentaRepository;
import java.util.List;

@Service
public class VentaService {
    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<Venta> listarTodas() { return ventaRepository.findAll(); }

    @Transactional
    public Venta registrarVenta(Venta venta) {
        Double total = 0.0;
        for (DetalleVenta detalle : venta.getDetalles()) {
            Producto producto = productoRepository.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // Verificación del control de inventario en tiempo real
            if (producto.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
            }

            // Actualización de stock en tiempo real
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoRepository.save(producto);

            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setVenta(venta);
            total += (detalle.getCantidad() * producto.getPrecio());
        }
        venta.setTotal(total);
        return ventaRepository.save(venta);
    }
}