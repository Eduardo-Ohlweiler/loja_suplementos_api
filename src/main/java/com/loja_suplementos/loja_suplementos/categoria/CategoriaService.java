package com.loja_suplementos.loja_suplementos.categoria;

import com.loja_suplementos.loja_suplementos.categoria.dtos.CategoriaDto;
import com.loja_suplementos.loja_suplementos.exceptions.ConflictException;
import com.loja_suplementos.loja_suplementos.exceptions.NotFoundException;
import com.loja_suplementos.loja_suplementos.usuario.UsuarioService;
import com.loja_suplementos.loja_suplementos.utils.ValidadorUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private ValidadorUsuario validadorUsuario;

    @Autowired
    private UsuarioService usuarioService;

    public List<Categoria> getAll(){
        return this.repository.findAll();
    }

    public Categoria findById(Integer id) throws NotFoundException {
        Optional<Categoria> categoria = this.repository.findById(id);
        if(categoria.isEmpty())
            throw new NotFoundException("Categoria não encontrada");

        return categoria.get();
    }

    public void delete(Integer deleteId){
        validadorUsuario.validarAdmin();
        Categoria categoria = this.findById(deleteId);
        this.repository.delete(categoria);
    }

    public Categoria create(CategoriaDto dto){

        Optional<Categoria> categoriaJaSalvo = repository.findByCategoriaNomeIgnoreCase(dto.getCategoriaNome());
        if (categoriaJaSalvo.isPresent())
            throw new ConflictException("Já existe cadastro com esse nome, verifique!");

        validadorUsuario.validarAdmin();

        Categoria categoria = new Categoria();
        categoria.setCategoria_nome(dto.getCategoriaNome());

        this.repository.save(categoria);
        return categoria;
    }

    public Categoria update(Integer id, CategoriaDto dto){
        Optional<Categoria> categoriaJaSalvo = repository.findByCategoriaNomeIgnoreCase(dto.getCategoriaNome());
        if (categoriaJaSalvo.isPresent() && !categoriaJaSalvo.get().getId().equals(id)) {
            throw new ConflictException("Já existe cadastro com esse nome, verifique!");
        }

        validadorUsuario.validarAdmin();

        Categoria categoria = this.findById(id);
        categoria.setCategoria_nome(dto.getCategoriaNome());

        return repository.save(categoria);
    }
}
