package com.loja_suplementos.loja_suplementos.produto;

import com.loja_suplementos.loja_suplementos.categoria.Categoria;
import com.loja_suplementos.loja_suplementos.categoria.CategoriaRepository;
import com.loja_suplementos.loja_suplementos.exceptions.NotFoundException;
import com.loja_suplementos.loja_suplementos.objetivo.Objetivo;
import com.loja_suplementos.loja_suplementos.objetivo.ObjetivoRepository;
import com.loja_suplementos.loja_suplementos.produto.dtos.CreateProdutoDto;
import com.loja_suplementos.loja_suplementos.produto.dtos.UpdateProdutoDto;
import com.loja_suplementos.loja_suplementos.utils.ValidadorUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ObjetivoRepository objetivoRepository;

    @Autowired
    private ValidadorUsuario validadorUsuario;

    public List<Produto> getAll(Integer categoriaId, Integer objetivoId){
        if (categoriaId != null)
            return produtoRepository.findByCategoriaId(categoriaId);

        if (objetivoId != null)
            return produtoRepository.findByObjetivoId(objetivoId);
        
        return this.produtoRepository.findAll();
    }

    public Produto create(CreateProdutoDto dto) {
        validadorUsuario.validarAdmin();

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));

        Objetivo objetivo = objetivoRepository.findById(dto.getObjetivoId())
                .orElseThrow(() -> new NotFoundException("Objetivo não encontrado"));

        Produto produto = new Produto();
        produto.setProdutoNome(dto.getProdutoNome());
        produto.setValor(dto.getValor());
        produto.setCategoria(categoria);
        produto.setObjetivo(objetivo);
        produto.setDescricao(dto.getDescricao());
        produto.setFoto(dto.getFoto());

        this.produtoRepository.save(produto);
        return produto;
    }

    public Produto update(Integer id, UpdateProdutoDto dto) {
        validadorUsuario.validarAdmin();

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

        if (dto.getProdutoNome() != null) {
            produto.setProdutoNome(dto.getProdutoNome());
        }

        if (dto.getValor() != null) {
            produto.setValor(dto.getValor());
        }

        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));
            produto.setCategoria(categoria);
        }

        if (dto.getObjetivoId() != null) {
            Objetivo objetivo = objetivoRepository.findById(dto.getObjetivoId())
                    .orElseThrow(() -> new NotFoundException("Objetivo não encontrado"));
            produto.setObjetivo(objetivo);
        }

        if (dto.getDescricao() != null) {
            produto.setDescricao(dto.getDescricao());
        }

        if (dto.getFoto() != null) {
            produto.setFoto(dto.getFoto());
        }

        return produtoRepository.save(produto);
    }

    public Produto findById(Integer id) throws NotFoundException {
        Optional<Produto> produto = this.produtoRepository.findById(id);
        if(produto.isEmpty())
            throw new NotFoundException("Produto não encontrado");

        return produto.get();
    }

    public void delete(Integer deleteId) {
        validadorUsuario.validarAdmin();
        Produto produto = produtoRepository.findById(deleteId)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));
        produtoRepository.delete(produto);
    }
}
