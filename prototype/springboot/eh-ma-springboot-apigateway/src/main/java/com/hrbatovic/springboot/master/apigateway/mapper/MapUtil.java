package com.hrbatovic.springboot.master.apigateway.mapper;

import com.hrbatovic.master.springboot.apigateway.clients.auth.model.AdminUpdateCredentialsRequest;
import com.hrbatovic.master.springboot.apigateway.clients.auth.model.LoginRequest;
import com.hrbatovic.master.springboot.apigateway.clients.auth.model.LoginResponse;
import com.hrbatovic.master.springboot.apigateway.clients.auth.model.RegisterRequest;
import com.hrbatovic.master.springboot.apigateway.clients.auth.model.RegisterResponse;
import com.hrbatovic.master.springboot.apigateway.clients.auth.model.SuccessResponse;
import com.hrbatovic.master.springboot.apigateway.clients.auth.model.UpdateCredentialsResponse;
import com.hrbatovic.master.springboot.apigateway.clients.auth.model.UserUpdateCredentialsRequest;
import com.hrbatovic.master.springboot.apigateway.clients.user.model.UpdateUserProfileRequest;
import com.hrbatovic.master.springboot.gateway.model.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class MapUtil {


    public abstract LoginRequest map(com.hrbatovic.master.springboot.gateway.model.LoginRequest loginRequest);

    public abstract com.hrbatovic.master.springboot.gateway.model.LoginResponse map(LoginResponse login);

    public abstract RegisterRequest map(com.hrbatovic.master.springboot.gateway.model.RegisterRequest registerRequest);

    public abstract com.hrbatovic.master.springboot.gateway.model.RegisterResponse map(RegisterResponse register);

    public abstract AdminUpdateCredentialsRequest map(com.hrbatovic.master.springboot.gateway.model.AdminUpdateCredentialsRequest adminUpdateCredentialsRequest);

    public abstract com.hrbatovic.master.springboot.gateway.model.SuccessResponse map(SuccessResponse successResponse);

    public abstract UserUpdateCredentialsRequest map(com.hrbatovic.master.springboot.gateway.model.UserUpdateCredentialsRequest userUpdateCredentialsRequest);

    public abstract com.hrbatovic.master.springboot.gateway.model.UpdateCredentialsResponse map(UpdateCredentialsResponse updateCredentialsResponse);

    public abstract DeleteUserResponse map(com.hrbatovic.master.springboot.apigateway.clients.user.model.DeleteUserResponse deleteUserResponse);

    public abstract UserProfileResponse map(com.hrbatovic.master.springboot.apigateway.clients.user.model.UserProfileResponse user);

    public abstract UserListResponse map(com.hrbatovic.master.springboot.apigateway.clients.user.model.UserListResponse userListResponse);

    public abstract UpdateUserProfileRequest map(com.hrbatovic.master.springboot.gateway.model.UpdateUserProfileRequest updateUserProfileRequest);

    public abstract Category map(com.hrbatovic.master.springboot.apigateway.clients.product.model.Category categoryById);

    public abstract CategoryListResponse map(com.hrbatovic.master.springboot.apigateway.clients.product.model.CategoryListResponse categoryListResponse);

    public abstract com.hrbatovic.master.springboot.apigateway.clients.product.model.CreateProductRequest map(CreateProductRequest createProductRequest);

    public abstract ProductResponse map(com.hrbatovic.master.springboot.apigateway.clients.product.model.ProductResponse product);

    public abstract DeleteProductResponse map(com.hrbatovic.master.springboot.apigateway.clients.product.model.DeleteProductResponse deleteProductResponse);

    public abstract ProductListResponse map(com.hrbatovic.master.springboot.apigateway.clients.product.model.ProductListResponse productListResponse);

    public abstract com.hrbatovic.master.springboot.apigateway.clients.product.model.UpdateProductRequest map(UpdateProductRequest updateProductRequest);

    public abstract com.hrbatovic.master.springboot.apigateway.clients.cart.model.AddCartProductRequest map(AddCartProductRequest addCartProductRequest);

    public abstract CartProductResponse map(com.hrbatovic.master.springboot.apigateway.clients.cart.model.CartProductResponse cartProductResponse);

    public abstract com.hrbatovic.master.springboot.apigateway.clients.cart.model.StartCheckoutRequest map(StartCheckoutRequest startCheckoutRequest);

    public abstract CheckoutResponse map(com.hrbatovic.master.springboot.apigateway.clients.cart.model.CheckoutResponse checkoutResponse);

    public abstract DeleteCartProductResponse map(com.hrbatovic.master.springboot.apigateway.clients.cart.model.DeleteCartProductResponse deleteCartProductResponse);

    public abstract EmptyCartResponse map(com.hrbatovic.master.springboot.apigateway.clients.cart.model.EmptyCartResponse emptyCartResponse);

    public abstract CartResponse map(com.hrbatovic.master.springboot.apigateway.clients.cart.model.CartResponse cartProducts);

    public abstract com.hrbatovic.master.springboot.apigateway.clients.cart.model.UpdateCartProductRequest map(UpdateCartProductRequest updateCartProductRequest);

    public abstract OrderListResponse map(com.hrbatovic.master.springboot.apigateway.clients.order.model.OrderListResponse allOrders);

    public abstract Order map(com.hrbatovic.master.springboot.apigateway.clients.order.model.Order orderById);

    public abstract com.hrbatovic.master.springboot.apigateway.clients.payment.model.CreatePaymentMethodRequest map(CreatePaymentMethodRequest createPaymentMethodRequest);

    public abstract PaymentMethodResponse map(com.hrbatovic.master.springboot.apigateway.clients.payment.model.PaymentMethodResponse paymentMethod);

    public abstract DeletePaymentMethodResponse map(com.hrbatovic.master.springboot.apigateway.clients.payment.model.DeletePaymentMethodResponse deletePaymentMethodResponse);

    public abstract PaymentMethodDetailedResponse map(com.hrbatovic.master.springboot.apigateway.clients.payment.model.PaymentMethodDetailedResponse paymentMethodById);

    public abstract PaymentMethodListResponse map(com.hrbatovic.master.springboot.apigateway.clients.payment.model.PaymentMethodListResponse paymentMethods);

    public abstract com.hrbatovic.master.springboot.apigateway.clients.payment.model.UpdatePaymentMethodRequest map(UpdatePaymentMethodRequest updatePaymentMethodRequest);

    public abstract LicenseListResponse map(com.hrbatovic.master.springboot.apigateway.clients.license.model.LicenseListResponse licenseListResponse);

    public abstract LicenseResponse map(com.hrbatovic.master.springboot.apigateway.clients.license.model.LicenseResponse licenseBySerialNumber);

    public abstract com.hrbatovic.master.springboot.apigateway.clients.license.model.CreateLicenseTemplateRequest map(CreateLicenseTemplateRequest createLicenseTemplateRequest);

    public abstract LicenseTemplateResponse map(com.hrbatovic.master.springboot.apigateway.clients.license.model.LicenseTemplateResponse licenseTemplate);

    public abstract DeleteLicenseTemplateResponse map(com.hrbatovic.master.springboot.apigateway.clients.license.model.DeleteLicenseTemplateResponse deleteLicenseTemplateResponse);

    public abstract LicenseTemplateListResponse map(com.hrbatovic.master.springboot.apigateway.clients.license.model.LicenseTemplateListResponse licenseTemplateListResponse);

    public abstract com.hrbatovic.master.springboot.apigateway.clients.license.model.UpdateLicenseTemplateRequest map(UpdateLicenseTemplateRequest updateLicenseTemplateRequest);

    public abstract NotificationResponse map(com.hrbatovic.master.springboot.apigateway.clients.notification.model.NotificationResponse notificationById);

    public abstract NotificationListResponse map(com.hrbatovic.master.springboot.apigateway.clients.notification.model.NotificationListResponse notificationListResponse);

    public abstract Event map(com.hrbatovic.master.springboot.apigateway.clients.tracking.model.Event eventById);

    public abstract EventListResponse map(com.hrbatovic.master.springboot.apigateway.clients.tracking.model.EventListResponse eventListResponse);
}
