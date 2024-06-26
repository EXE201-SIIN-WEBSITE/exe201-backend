package com.example.exe202backend.repositories;

import com.example.exe202backend.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCartId(Long cartId);

    @Query("SELECT ci FROM CartItem ci " +
            "WHERE ci.cart.id = :cartId " +
            "AND ci.productMaterial.id = :materialId")
    CartItem findByCartIdAndMaterialId(@Param("cartId") Long cartId, @Param("materialId") Long materialId);
}
