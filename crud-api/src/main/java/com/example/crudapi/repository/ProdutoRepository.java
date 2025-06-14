package com.example.crudapi.repository;

import com.example.crudapi.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Buscar produtos por nome (contendo o texto)
    List<Produto> findByNomeContainingIgnoreCase(String nome);

    // Buscar produtos por categoria
    List<Produto> findByCategoria(String categoria);

    // Buscar produtos ativos
    List<Produto> findByAtivoTrue();

    // Buscar produtos por categoria e status ativo
    List<Produto> findByCategoriaAndAtivo(String categoria, Boolean ativo);

    // Buscar produtos com estoque baixo
    @Query("SELECT p FROM Produto p WHERE p.quantidadeEstoque <= :limite")
    List<Produto> findProdutosComEstoqueBaixo(@Param("limite") Long limite);

    // Buscar produtos por faixa de preço
    @Query("SELECT p FROM Produto p WHERE p.preco BETWEEN :precoMin AND :precoMax")
    List<Produto> findByPrecoRange(@Param("precoMin") Double precoMin, @Param("precoMax") Double precoMax);
}

