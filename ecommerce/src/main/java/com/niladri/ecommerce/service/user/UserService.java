package com.niladri.ecommerce.service.user;

import com.niladri.ecommerce.model.user.UserModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceInterface {
    @Override
    public UserDetails loadUserByUsername(String username) {
        return null;
    }
}
