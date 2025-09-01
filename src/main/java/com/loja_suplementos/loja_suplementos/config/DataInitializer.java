package com.loja_suplementos.loja_suplementos.config;

import com.loja_suplementos.loja_suplementos.usuario.Role;
import com.loja_suplementos.loja_suplementos.usuario.Usuario;
import com.loja_suplementos.loja_suplementos.usuario.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            // Verifica se já existe algum usuário com role ADMIN
            boolean adminExists = usuarioRepository.existsByRole(Role.ADMIN);

            if (!adminExists) {
                Usuario admin = new Usuario();
                admin.setNome("LojaSuplementos");
                admin.setEmail("admin@admin.com");
                admin.setPassword(passwordEncoder.encode("Admin12345@")); // senha criptografada
                admin.setRole(Role.ADMIN);

                usuarioRepository.save(admin);
                System.out.println("Usuário admin criado com sucesso!");
            } else {
                System.out.println("Usuário admin já existe");
            }
        };
    }
}
