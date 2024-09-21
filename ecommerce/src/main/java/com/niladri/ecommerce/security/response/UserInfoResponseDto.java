package com.niladri.ecommerce.security.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponseDto {
    private Long userId;
    private String email;
    private List<String> roles;
    private String username;
    private String token;


    public UserInfoResponseDto(Long userId, String email, List<String> roles, String username) {
        this.userId = userId;
        this.email = email;
        this.roles = roles;
        this.username = username;
    }




}
