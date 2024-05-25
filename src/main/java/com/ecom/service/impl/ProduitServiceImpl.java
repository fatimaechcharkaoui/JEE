package com.ecom.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import com.ecom.model.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.repository.ProduitRepository;
import com.ecom.service.ProduitService;

@Service
public class ProduitServiceImpl implements ProduitService {

	@Autowired
	private ProduitRepository produitRepository;

	@Override
	public Produit saveProduct(Produit produit) {
		return produitRepository.save(produit);
	}

	@Override
	public List<Produit> getAllProducts() {
		return produitRepository.findAll();
	}

	@Override
	public Boolean deleteProduct(Integer id) {
		Produit produit = produitRepository.findById(id).orElse(null);

		if (!ObjectUtils.isEmpty(produit)) {
			produitRepository.delete(produit);
			return true;
		}
		return false;
	}

	@Override
	public Produit getProductById(Integer id) {
		Produit produit = produitRepository.findById(id).orElse(null);
		return produit;
	}

	@Override
	public Produit updateProduct(Produit produit, MultipartFile image) {

		Produit dbProduit = getProductById(produit.getId());

		String imageName = image.isEmpty() ? dbProduit.getImage() : image.getOriginalFilename();

		dbProduit.setTitle(produit.getTitle());
		dbProduit.setDescription(produit.getDescription());
		dbProduit.setCategory(produit.getCategory());
		dbProduit.setPrice(produit.getPrice());
		dbProduit.setStock(produit.getStock());
		dbProduit.setImage(imageName);
		dbProduit.setIsActive(produit.getIsActive());
		dbProduit.setDiscount(produit.getDiscount());

		// 5=100*(5/100); 100-5=95
		Double disocunt = produit.getPrice() * (produit.getDiscount() / 100.0);
		Double discountPrice = produit.getPrice() - disocunt;
		dbProduit.setDiscountPrice(discountPrice);

		Produit updateProduit = produitRepository.save(dbProduit);

		if (!ObjectUtils.isEmpty(updateProduit)) {

			if (!image.isEmpty()) {

				try {
					File saveFile = new ClassPathResource("static/img").getFile();

					Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "product_img" + File.separator
							+ image.getOriginalFilename());
					Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return produit;
		}
		return null;
	}

	@Override
	public List<Produit> getAllActiveProducts(String category) {
		List<Produit> produits = null;
		if (ObjectUtils.isEmpty(category)) {
			produits = produitRepository.findByIsActiveTrue();
		}else {
			produits = produitRepository.findByCategory(category);
		}

		return produits;
	}

}
