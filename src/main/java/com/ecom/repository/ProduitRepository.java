package com.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.model.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Integer> {

	List<Produit> findByIsActiveTrue();

	List<Produit> findByCategory(String category);

}
