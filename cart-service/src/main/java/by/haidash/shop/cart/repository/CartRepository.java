package by.haidash.shop.cart.repository;

import by.haidash.shop.cart.entity.Cart;
import by.haidash.shop.cart.entity.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c where c.user = :user and c.status IN :statuses")
    Optional<Cart> findByUserAndStatuses(@Param("user") Long user, @Param("statuses") List<CartStatus> statuses);

}
