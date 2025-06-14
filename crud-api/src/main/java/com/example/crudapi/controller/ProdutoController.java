package com.example.crudapi.controller;

import com.example.crudapi.entity.Produto;
import com.example.crudapi.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // CREATE - Criar novo produto
    @PostMapping
    public ResponseEntity<Produto> criarProduto(@Valid @RequestBody Produto produto) {
        try {
            Produto novoProduto = produtoService.criarProduto(produto);
            return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - Listar todos os produtos
    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        try {
            List<Produto> produtos = produtoService.listarTodos();
            if (produtos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(produtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // READ - Buscar produto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable("id") Long id) {
        Optional<Produto> produto = produtoService.buscarPorId(id);
        if (produto.isPresent()) {
            return new ResponseEntity<>(produto.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // UPDATE - Atualizar produto
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable("id") Long id, @Valid @RequestBody Produto produto) {
        try {
            Produto produtoAtualizado = produtoService.atualizarProduto(id, produto);
            return new ResponseEntity<>(produtoAtualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE - Excluir produto
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> excluirProduto(@PathVariable("id") Long id) {
        try {
            produtoService.excluirProduto(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // SEARCH - Buscar produtos por nome
    @GetMapping("/buscar/nome")
    public ResponseEntity<List<Produto>> buscarPorNome(@RequestParam String nome) {
        try {
            List<Produto> produtos = produtoService.buscarPorNome(nome);
            if (produtos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(produtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // SEARCH - Buscar produtos por categoria
    @GetMapping("/buscar/categoria")
    public ResponseEntity<List<Produto>> buscarPorCategoria(@RequestParam String categoria) {
        try {
            List<Produto> produtos = produtoService.buscarPorCategoria(categoria);
            if (produtos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(produtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // SEARCH - Buscar produtos ativos
    @GetMapping("/ativos")
    public ResponseEntity<List<Produto>> buscarProdutosAtivos() {
        try {
            List<Produto> produtos = produtoService.buscarProdutosAtivos();
            if (produtos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(produtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // SEARCH - Buscar produtos com estoque baixo
    @GetMapping("/estoque-baixo")
    public ResponseEntity<List<Produto>> buscarEstoqueBaixo(@RequestParam(defaultValue = "10") Long limite) {
        try {
            List<Produto> produtos = produtoService.buscarProdutosComEstoqueBaixo(limite);
            if (produtos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(produtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // SEARCH - Buscar produtos por faixa de preço
    @GetMapping("/buscar/preco")
    public ResponseEntity<List<Produto>> buscarPorFaixaPreco(
            @RequestParam Double precoMin, 
            @RequestParam Double precoMax) {
        try {
            List<Produto> produtos = produtoService.buscarPorFaixaPreco(precoMin, precoMax);
            if (produtos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(produtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Estatísticas - Contar produtos
    @GetMapping("/count")
    public ResponseEntity<Long> contarProdutos() {
        try {
            long count = produtoService.contarProdutos();
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

