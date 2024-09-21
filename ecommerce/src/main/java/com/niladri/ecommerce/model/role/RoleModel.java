package com.niladri.ecommerce.model.role;

import com.niladri.ecommerce.model.user.UserModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;


    @ToString.Exclude
    @Column(length = 20,name = "role_name")
    @Enumerated(EnumType.STRING)
    private AppRoles roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<UserModel> users = new HashSet<>();

    public RoleModel(AppRoles roleName) {
        this.roleName = roleName;
    }
}
