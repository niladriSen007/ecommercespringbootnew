package com.niladri.ecommerce.repository.role;

import com.niladri.ecommerce.model.role.AppRoles;
import com.niladri.ecommerce.model.role.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<RoleModel,Long> {
    Optional<RoleModel> findByRoleName(AppRoles roleName);
}
