package uts.edu.java.proyecto;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import uts.edu.java.proyecto.model.Usuario;
import uts.edu.java.proyecto.repository.UsuarioRepository;

@SpringBootApplication
public class ProyectoFinalJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProyectoFinalJavaApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(UsuarioRepository usuarioRepository) {
        return args -> {
            if (usuarioRepository.count() == 0) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
                admin.setRol("ROLE_ADMIN");
                admin.setNombreCompleto("Administrador del Sistema");
                usuarioRepository.save(admin);
                System.out.println("Usuario 'admin' creado con contraseña 'admin'");
            }
        };
    }
}