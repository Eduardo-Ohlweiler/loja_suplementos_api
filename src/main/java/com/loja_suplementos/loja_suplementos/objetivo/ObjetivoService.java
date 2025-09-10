package com.loja_suplementos.loja_suplementos.objetivo;

import com.loja_suplementos.loja_suplementos.exceptions.ConflictException;
import com.loja_suplementos.loja_suplementos.exceptions.NotFoundException;
import com.loja_suplementos.loja_suplementos.objetivo.dtos.ObjetivoDto;
import com.loja_suplementos.loja_suplementos.usuario.UsuarioService;
import com.loja_suplementos.loja_suplementos.utils.ValidadorUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObjetivoService {
    @Autowired
    private ObjetivoRepository repository;

    @Autowired
    private ValidadorUsuario validadorUsuario;

    @Autowired
    private UsuarioService usuarioService;

    public List<Objetivo> getAll(){
        return this.repository.findAll();
    }

    public Objetivo findById(Integer id) throws NotFoundException {
        Optional<Objetivo> objetivo = this.repository.findById(id);
        if(objetivo.isEmpty())
            throw new NotFoundException("Objetivo não encontrado");

        return objetivo.get();
    }

    public void delete(Integer deleteId){
        validadorUsuario.validarAdmin();
        Objetivo objetivo = this.findById(deleteId);
        this.repository.delete(objetivo);
    }

    public Objetivo create(ObjetivoDto dto){

        Optional<Objetivo> objetivoJaSalvo = repository.findByObjetivoNomeIgnoreCase(dto.getObjetivoNome());
        if (objetivoJaSalvo.isPresent())
            throw new ConflictException("Já existe cadastro com esse nome, verifique!");

        validadorUsuario.validarAdmin();

        Objetivo objetivo = new Objetivo();
        objetivo.setObjetivo_nome(dto.getObjetivoNome());

        this.repository.save(objetivo);
        return objetivo;
    }

    public Objetivo update(Integer id, ObjetivoDto dto){
        Optional<Objetivo> objetivoJaSalvo = repository.findByObjetivoNomeIgnoreCase(dto.getObjetivoNome());
        if (objetivoJaSalvo.isPresent() && !objetivoJaSalvo.get().getId().equals(id)) {
            throw new ConflictException("Já existe cadastro com esse nome, verifique!");
        }

        validadorUsuario.validarAdmin();

        Objetivo objetivo = this.findById(id);
        objetivo.setObjetivo_nome(dto.getObjetivoNome());

        return repository.save(objetivo);
    }
}
