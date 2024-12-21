package com.hrbatovic.quarkus.master.cart.api.validators;

import com.hrbatovic.master.quarkus.cart.model.AddCartProductRequest;
import com.hrbatovic.master.quarkus.cart.model.StartCheckoutRequest;
import com.hrbatovic.master.quarkus.cart.model.UpdateCartProductRequest;
import com.hrbatovic.quarkus.master.cart.exceptions.EhMaException;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public abstract class ApiInputValidator {


    public static void validateAddProductToCart(AddCartProductRequest addCartProductRequest) {
        if (addCartProductRequest == null || addCartProductRequest.getProductId() == null || StringUtils.isEmpty(addCartProductRequest.getProductId().toString())) {
            throw new EhMaException(400, "Not all information provided.");
        }

    }

    public static void validateCheckoutCart(StartCheckoutRequest startCheckoutRequest) {
        if(startCheckoutRequest == null || StringUtils.isEmpty(startCheckoutRequest.getPaymentMethod()) ||
        startCheckoutRequest.getPaymentToken() == null || StringUtils.isEmpty(startCheckoutRequest.getPaymentToken().toString())){
            throw new EhMaException(400, "Not all checkout information provided.");
        }
    }

    public static void validateProductId(UUID productId) {
        if(productId == null || StringUtils.isEmpty(productId.toString())){
            throw new EhMaException(400, "Product id must not be empty");
        }
    }

    public static void validateUpdateCartProduct(UpdateCartProductRequest updateCartProductRequest) {
        if(updateCartProductRequest == null ){
            throw new EhMaException(400, "Not all update information provided");
        }
    }

    public static void validateQuantity(Integer quantity){
        if (quantity == null || quantity < 1) {
            throw new EhMaException(400, "Invalid quantity. Quantity must be at least 1.");
        }
    }
}
