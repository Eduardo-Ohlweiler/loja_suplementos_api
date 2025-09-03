package com.loja_suplementos.loja_suplementos.utils;

import com.loja_suplementos.loja_suplementos.exceptions.ForbiddenException;
import com.loja_suplementos.loja_suplementos.exceptions.UnauthorizedException;
import com.loja_suplementos.loja_suplementos.usuario.Role;
import com.loja_suplementos.loja_suplementos.usuario.Usuario;
import com.loja_suplementos.loja_suplementos.usuario.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ValidadorUsuario {
    private final UsuarioRepository usuarioRepository;

    public ValidadorUsuario(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    private Usuario getUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal() == null) {
            throw new UnauthorizedException("Usuário não autenticado");
        }

        Integer usuarioId = (Integer) auth.getPrincipal();
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UnauthorizedException("Usuário não encontrado"));
    }

    public void validarAdmin() {
        Usuario usuarioLogado = getUsuarioLogado();
        if (usuarioLogado.getRole() != Role.ADMIN) {
            throw new ForbiddenException("Acesso negado: apenas administradores podem realizar esta operação");
        }
    }
}
