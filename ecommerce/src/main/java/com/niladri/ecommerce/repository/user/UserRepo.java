package com.niladri.ecommerce.repository.user;

import com.niladri.ecommerce.model.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserModel,Long> {

    Optional<UserModel> findByUserName(String username);

    Boolean existsByUserName(String user1);
}
