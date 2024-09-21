package com.niladri.ecommerce.model.user;

import com.niladri.ecommerce.model.address.AddressModel;
import com.niladri.ecommerce.model.product.ProductModel;
import com.niladri.ecommerce.model.role.RoleModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users",uniqueConstraints = {
        @UniqueConstraint(columnNames = "userName"),
        @UniqueConstraint(columnNames = "userEmail"),
})
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank(message = "User name is required")
    @Size(min = 3, max = 50, message = "User name should be between 3 to 50 characters")
    @Column(name = "user_name")
    private String userName;

    @NotBlank(message = "User email is required")
    @Size(min = 3, max = 50, message = "User email should be between 6 to 50 characters")
    @Email(message = "Invalid email")
    @Column(name = "user_email")
    private String userEmail;

    @NotBlank(message = "User password is required")
    @Size(min = 1, max = 50, message = "User password should be between 6 to 50 characters")
    @Column(name = "user_password")
    private String userPassword;

    @Getter
    @Setter
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleModel> roles = new HashSet<>();


    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.MERGE},
    orphanRemoval = true)
    private Set<ProductModel> products;

    @Getter
    @Setter
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_address",
            joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<AddressModel> addresses;


    public UserModel(String userName, String userEmail, String userPassword) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }


//    public Throwable getAuthorities() {
//    }
}
