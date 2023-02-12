package com.microservice.product.cloud.mapper;

import com.microservice.product.cloud.model.Product;
import com.microservice.product.cloud.record.ProductCreatedRequest;
import com.microservice.product.cloud.record.ProductDTO;
import com.microservice.product.cloud.record.ProductUpdateRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static Product get(ProductCreatedRequest request){
        Product product = new Product();
        product.setProductTag(request.productTag());
        product.setDescription(request.description());
        product.setPrice(request.price());
        return product;
    }

    public static Product get(ProductUpdateRequest request){
        Product product = new Product();
        product.setId(request.id());
        product.setProductTag(request.productTag());
        product.setDescription(request.description());
        product.setPrice(request.price());
        return product;
    }

    public static  List<ProductDTO> getOutputList(List<Product> products) {

        List<ProductDTO> productDTOList =  new ArrayList<>();
        for (Product prod :products ) {
            ProductDTO dto = new ProductDTO(prod.getId(), prod.getProductCode(), prod.getProductTag(), prod.getDescription(), prod.getPrice());
            productDTOList.add(dto);
        }
        return productDTOList;
    }


}
