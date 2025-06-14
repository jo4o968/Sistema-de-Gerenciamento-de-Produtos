package com.example.crudapi.service;

import com.example.crudapi.entity.Produto;
import com.example.crudapi.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    // CREATE - Criar um novo produto
    public Produto criarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    // READ - Listar todos os produtos
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    // READ - Buscar produto por ID
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    // UPDATE - Atualizar produto
    public Produto atualizarProduto(Long id, Produto produtoAtualizado) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setNome(produtoAtualizado.getNome());
                    produto.setDescricao(produtoAtualizado.getDescricao());
                    produto.setPreco(produtoAtualizado.getPreco());
                    produto.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());
                    produto.setCategoria(produtoAtualizado.getCategoria());
                    produto.setAtivo(produtoAtualizado.getAtivo());
                    produto.setCodigoBarras(produtoAtualizado.getCodigoBarras());
                    return produtoRepository.save(produto);
                })
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
    }

    // DELETE - Excluir produto
    public void excluirProduto(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }
    }

    // SEARCH - Buscar produtos por nome
    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    // SEARCH - Buscar produtos por categoria
    public List<Produto> buscarPorCategoria(String categoria) {
        return produtoRepository.findByCategoria(categoria);
    }

    // SEARCH - Buscar produtos ativos
    public List<Produto> buscarProdutosAtivos() {
        return produtoRepository.findByAtivoTrue();
    }

    // SEARCH - Buscar produtos com estoque baixo
    public List<Produto> buscarProdutosComEstoqueBaixo(Long limite) {
        return produtoRepository.findProdutosComEstoqueBaixo(limite);
    }

    // SEARCH - Buscar produtos por faixa de preço
    public List<Produto> buscarPorFaixaPreco(Double precoMin, Double precoMax) {
        return produtoRepository.findByPrecoRange(precoMin, precoMax);
    }

    // Verificar se produto existe
    public boolean existeProduto(Long id) {
        return produtoRepository.existsById(id);
    }

    // Contar total de produtos
    public long contarProdutos() {
        return produtoRepository.count();
    }
}

