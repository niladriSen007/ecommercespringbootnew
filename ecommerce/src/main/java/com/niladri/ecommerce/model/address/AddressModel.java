package com.niladri.ecommerce.model.address;

import com.niladri.ecommerce.model.user.UserModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class AddressModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Size(min = 3, max = 50, message = "Street name should be between 3 to 50 characters")
    private String street;

    @NotBlank
    @Size(min = 3, max = 50, message = "Building name should be between 3 to 50 characters")
    private String building;

    @NotBlank
    @Size(min = 3, max = 50, message = "City name should be between 3 to 50 characters")
    private String city;

    @NotBlank
    @Size(min = 3, max = 50, message = "State name should be between 3 to 50 characters")
    private String state;

    @NotBlank
    @Size(min = 3, max = 50, message = "Country name should be between 3 to 50 characters")
    private String country;

    @NotBlank
    @Size(min = 3, max = 50, message = "Zip code should be between 3 to 50 characters")
    private String zipCode;

    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    private List<UserModel> users = new ArrayList<>();

    public AddressModel(String street, String building, String city, String state, String country, String zipCode) {
        this.street = street;
        this.building = building;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }


}
