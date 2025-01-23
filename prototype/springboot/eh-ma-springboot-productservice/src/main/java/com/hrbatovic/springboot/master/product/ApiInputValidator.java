package com.hrbatovic.springboot.master.product;

import com.hrbatovic.master.springboot.product.model.CreateProductRequest;
import com.hrbatovic.master.springboot.product.model.UpdateProductRequest;
import com.hrbatovic.springboot.master.product.exceptions.EhMaException;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public abstract class ApiInputValidator {
    public static void validateProductId(UUID productId) {
        if (productId == null || StringUtils.isEmpty(productId.toString())) {
            throw new EhMaException(400, "Product id must not be empty");
        }
    }


    public static void validateUpdateProduct(UpdateProductRequest updateProductRequest) {
        if (updateProductRequest == null || StringUtils.isEmpty(updateProductRequest.getCategory()) ||
                StringUtils.isEmpty(updateProductRequest.getDescription()) ||
                StringUtils.isEmpty(updateProductRequest.getTitle()) ||
                StringUtils.isEmpty(updateProductRequest.getImageUrl()) ||
                updateProductRequest.getDeleted() == null ||
                updateProductRequest.getPrice() == null ||
                updateProductRequest.getTags() == null || updateProductRequest.getTags().isEmpty()
        ) {

            throw new EhMaException(400, "Not all update product information provided.");
        }
    }

    public static void validateCreateProdocut(CreateProductRequest createProductRequest) {
        if (createProductRequest == null || StringUtils.isEmpty(createProductRequest.getCategory()) ||
                StringUtils.isEmpty(createProductRequest.getDescription()) ||
                StringUtils.isEmpty(createProductRequest.getTitle()) ||
                StringUtils.isEmpty(createProductRequest.getImageUrl()) ||
                createProductRequest.getTags() == null || createProductRequest.getTags().isEmpty() ||
                createProductRequest.getPrice() == null
        ) {
            throw new EhMaException(400, "Not all create product information provided");
        }
    }

    public static void validateCategoryId(UUID id) {
        if(id == null || StringUtils.isEmpty(id.toString())){
            throw new EhMaException(400, "Category id must not be empty.");
        }
    }
}
