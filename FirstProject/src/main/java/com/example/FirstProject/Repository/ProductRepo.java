package com.example.FirstProject.Repository;

import com.example.FirstProject.model.Category;
import com.example.FirstProject.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Page<Product> findByCategoryOrderByPriceAsc(Category category, Pageable pageableDetails);
    Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageableDetails);
}
