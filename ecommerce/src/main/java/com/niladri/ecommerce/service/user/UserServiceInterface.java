package com.niladri.ecommerce.service.user;

import com.niladri.ecommerce.model.user.UserModel;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserServiceInterface {
    UserDetails loadUserByUsername(String username);
}
