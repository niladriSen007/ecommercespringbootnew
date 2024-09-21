package com.niladri.ecommerce.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.niladri.ecommerce.model.user.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsCustom implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsCustom build(UserModel user) {
        List<SimpleGrantedAuthority> authorities =
                                        user.getRoles().stream()
                                                .map(role -> new SimpleGrantedAuthority
                                                        (role.getRoleName().name())).toList();
        return new UserDetailsCustom(
                user.getUserId(),
                user.getUserName(),
                user.getUserEmail(),
                user.getUserPassword(), authorities);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsCustom user = (UserDetailsCustom) o;
        return Objects.equals(userId, user.userId);
    }


}
