package com.example.exe202backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductDTO {
    private String name;
    private String coverImage;
    private double price;
    private String status;
    private int quantity;
    private long categoryId;
    private long accessoryId;
}