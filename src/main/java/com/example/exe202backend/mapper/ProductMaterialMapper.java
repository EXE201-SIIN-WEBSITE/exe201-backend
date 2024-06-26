package com.example.exe202backend.mapper;

import com.example.exe202backend.dto.CreateProductMaterialDTO;
import com.example.exe202backend.dto.ProductMaterialDTO;
import com.example.exe202backend.models.ProductMaterial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMaterialMapper {
    @Mapping(target = "status", source = "status")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "colorId", source = "color.id")
    @Mapping(target = "sizeId", source = "size.id")
    @Mapping(target = "accessoryId", source = "accessory.id")
    ProductMaterialDTO toDto(ProductMaterial productMaterial);

    @Mapping(target = "status", source = "status")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "color", ignore = true)
    @Mapping(target = "size", ignore = true)
    @Mapping(target = "accessory", ignore = true)
    ProductMaterial toEntity(ProductMaterialDTO productMaterialDTO);

    @Mapping(target = "status", source = "status")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "color", ignore = true)
    @Mapping(target = "size", ignore = true)
    @Mapping(target = "accessory", ignore = true)
    void updateProductMaterialFromDto(ProductMaterialDTO productMaterialDTO,
                                      @MappingTarget ProductMaterial productMaterial);

    @Mapping(target = "status", source = "status")
    @Mapping(target = "id", source = "id")
    ProductMaterial CreateDtotoEntity(CreateProductMaterialDTO productMaterialDTO);
}
