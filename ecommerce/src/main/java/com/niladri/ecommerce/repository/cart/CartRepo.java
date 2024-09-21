package com.niladri.ecommerce.repository.cart;

import com.niladri.ecommerce.model.cart.CartModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<CartModel,Long> {
}
