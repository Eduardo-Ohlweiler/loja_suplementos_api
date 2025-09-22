package com.loja_suplementos.loja_suplementos.auth;

import com.loja_suplementos.loja_suplementos.auth.dtos.LoginDto;
import com.loja_suplementos.loja_suplementos.auth.dtos.LoginResponseDto;
import com.loja_suplementos.loja_suplementos.exceptions.UnauthorizedException;
import com.loja_suplementos.loja_suplementos.usuario.Usuario;
import com.loja_suplementos.loja_suplementos.usuario.UsuarioService;
import com.loja_suplementos.loja_suplementos.usuario.dtos.UsuarioCreateDto;
import com.loja_suplementos.loja_suplementos.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UsuarioService userService;

    @Autowired
    private JwtUtil jwtUtil;

    public Usuario create(UsuarioCreateDto dto){
        return this.userService.create(dto, null);
    }


    public LoginResponseDto login(LoginDto dto){
        Usuario usuario  = this.userService.findByEmail(dto.getEmail());
        Boolean match    = this.userService.compararSenha(dto.getPassword_hash(), usuario);

        if(!match)
            throw new UnauthorizedException("Credenciais inválidas");
        usuario.setPassword(null);

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setAccessToken(jwtUtil.gerar(usuario.getId(), usuario.getRole()));
        loginResponseDto.setUsuario(usuario);

        return loginResponseDto;
    }
}
