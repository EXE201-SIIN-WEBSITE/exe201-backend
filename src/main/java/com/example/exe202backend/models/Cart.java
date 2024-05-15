package com.example.exe202backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
public class Cart extends BaseModel{
    private double total;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel userModel;
}