package uts.edu.java.proyecto.service;

import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uts.edu.java.proyecto.model.Usuario;
import uts.edu.java.proyecto.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // CORRECCIÓN DE TIPO: Se usa el método .orElse(null) para desempaquetar correctamente el Optional
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado en el sistema ERP: " + username);
        }

        // Obtener el rol y asegurar que no sea nulo
        String nombreRol = usuario.getRol();
        if (nombreRol == null) {
            nombreRol = "ADMIN"; 
        }
        
        // Estandarizar el rol con el prefijo 'ROLE_' requerido por Spring Security
        if (!nombreRol.startsWith("ROLE_")) {
            nombreRol = "ROLE_" + nombreRol;
        }

        // Construir el usuario oficial de Spring con las credenciales limpias
        return new org.springframework.security.core.userdetails.User(
            usuario.getUsername(),
            usuario.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority(nombreRol))
        );
    }
}