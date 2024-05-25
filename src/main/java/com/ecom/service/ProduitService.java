package com.ecom.service;

import java.util.List;

import com.ecom.model.Produit;
import org.springframework.web.multipart.MultipartFile;

public interface ProduitService {

	public Produit saveProduct(Produit produit);

	public List<Produit> getAllProducts();

	public Boolean deleteProduct(Integer id);

	public Produit getProductById(Integer id);

	public Produit updateProduct(Produit produit, MultipartFile file);

	public List<Produit> getAllActiveProducts(String category);
	
}
