package com.loja_suplementos.loja_suplementos.usuario;

import com.loja_suplementos.loja_suplementos.exceptions.ConflictException;
import com.loja_suplementos.loja_suplementos.exceptions.NotFoundException;
import com.loja_suplementos.loja_suplementos.exceptions.UnauthorizedException;
import com.loja_suplementos.loja_suplementos.usuario.dtos.UsuarioCreateDto;
import com.loja_suplementos.loja_suplementos.usuario.dtos.UsuarioUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> getAll(){
        return this.repository.findAll();
    }

    public Usuario findByEmail(String email) throws NotFoundException {
        Optional<Usuario> usuario = this.repository.findByEmail(email);
        if(usuario.isEmpty())
            throw new NotFoundException("Usuario não encontrado");

        return usuario.get();
    }

    public Usuario findById(Integer id) throws NotFoundException{
        Optional<Usuario> usuario = this.repository.findById(id);
        if(usuario.isEmpty())
            throw new NotFoundException("Usuario não encontrado");

        return usuario.get();
    }

    public Usuario create(UsuarioCreateDto dto, Integer usuarioId){
        boolean usuario_existe = this.repository.existsByEmail(dto.getEmail());
        if(usuario_existe)
            throw new ConflictException("Já existe usuario com esse email cadastrado, verifique");

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));

        if(usuarioId != null){
            Usuario usuario_logado = this.findById(usuarioId);
            if(usuario_logado != null){
                if(usuario_logado.getRole() == Role.ADMIN){
                    usuario.setRole(dto.getRole());
                }
            }
        }

        this.repository.save(usuario);
        usuario.setPassword(null);
        return usuario;
    }

    public Usuario update(Integer id, UsuarioUpdateDto dto, Integer usuarioLogadoId){

        if(usuarioLogadoId == null)
            throw new UnauthorizedException("Acesso negado: Apenas usuarios autenticados podem realizar essa ação");
        Usuario usuario = this.findById(id);

        Usuario usuarioLogado = this.findById(usuarioLogadoId);

        if (usuarioLogado.getRole() == Role.USER && !usuarioLogado.getId().equals(usuario.getId())) {
            throw new UnauthorizedException("Acesso negado: Apenas administradores podem editar usuários");
        }
        if(usuarioLogado.getRole() == Role.ADMIN){
            usuario.setRole(dto.getRole());
        }

        if(dto.getNome() != null)
            usuario.setNome(dto.getNome());
        if(dto.getEmail() != null)
            usuario.setEmail(dto.getEmail());
        if(dto.getTelefone() != null)
            usuario.setTelefone(dto.getTelefone());
        if(dto.getPassword() != null)
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));

        this.repository.save(usuario);
        usuario.setPassword(null);
        return usuario;
    }

    public void delete(Integer deleteId, Usuario usuarioLogado){
        if(usuarioLogado.getRole() != Role.ADMIN)
            throw new UnauthorizedException("Acesso negado: apenas administradores podem deletar usuários");

        Usuario usuario = this.findById(deleteId);
        this.repository.delete(usuario);
    }

    public Boolean compararSenha(String senha, Usuario usuario){
        return this.passwordEncoder.matches(senha, usuario.getPassword());
    }
}
