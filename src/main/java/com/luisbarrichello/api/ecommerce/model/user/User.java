package com.luisbarrichello.api.ecommerce.model.user;


import com.luisbarrichello.api.ecommerce.dto.user.UserCreateDTO;
import com.luisbarrichello.api.ecommerce.model.address.Address;
import com.luisbarrichello.api.ecommerce.model.shoppingCart.ShoppingCart;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "users")
@Entity(name = "User")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String username;
    private String phoneNumber;

    private Boolean isActive;
    private Boolean emailVerified;
    private String resetPasswordToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> address;

    @Enumerated(EnumType.STRING)
    private RoleUser role;

    private LocalDateTime lastLogin;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;

    public User(UserCreateDTO userCreateDTO) {
        this.name = userCreateDTO.name();
        this.email = userCreateDTO.email();
        this.password = userCreateDTO.password();
        this.username = userCreateDTO.username();
        this.phoneNumber = userCreateDTO.phoneNumber();
        this.role = userCreateDTO.role();
        this.address = userCreateDTO.address();
        this.isActive = true;
        this.emailVerified = false;
        this.createAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        createAt = LocalDateTime.now();
        isActive = true;
        emailVerified = false;
    }

    @PreUpdate
    protected void onUpdate() {
        updateAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getActive() {
        return isActive;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public RoleUser getRole() {
        return role;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void setRole(RoleUser role) {
        this.role = role;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
