package com.example.FirstProject.Repository;

import com.example.FirstProject.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {

@Query("select ci from CartItem ci where ci.cart.cartId=?1" +
        " and ci.product.productId=?2")
    CartItem findCartItemByProductIdAndCartId(Long cartId, Long productId);
@Modifying
    @Query("delete from CartItem ci   where ci.product.productId=?2 and ci.cart.cartId=?1")
    void deleteCartItemByProductIdAndCartId(Long cartId, Long productId);
}
