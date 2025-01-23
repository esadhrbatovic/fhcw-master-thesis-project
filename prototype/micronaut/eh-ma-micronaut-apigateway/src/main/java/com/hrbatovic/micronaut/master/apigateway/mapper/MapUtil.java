package com.hrbatovic.micronaut.master.apigateway.mapper;

import com.hrbatovic.master.micronaut.apigateway.clients.auth.model.AdminUpdateCredentialsRequest;
import com.hrbatovic.master.micronaut.apigateway.clients.auth.model.LoginRequest;
import com.hrbatovic.master.micronaut.apigateway.clients.auth.model.RegisterRequest;
import com.hrbatovic.master.micronaut.apigateway.clients.auth.model.UserUpdateCredentialsRequest;
import com.hrbatovic.micronaut.master.apigateway.model.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jsr330")
public abstract class MapUtil {

    public abstract LoginRequest map(com.hrbatovic.micronaut.master.apigateway.model.LoginRequest loginRequest);

    public abstract LoginResponse map(com.hrbatovic.master.micronaut.apigateway.clients.auth.model.LoginResponse login);

    public abstract RegisterResponse map(com.hrbatovic.master.micronaut.apigateway.clients.auth.model.RegisterResponse register);

    public abstract RegisterRequest map(com.hrbatovic.micronaut.master.apigateway.model.RegisterRequest registerRequest);

    public abstract AdminUpdateCredentialsRequest map(com.hrbatovic.micronaut.master.apigateway.model.AdminUpdateCredentialsRequest adminUpdateCredentialsRequest);

    public abstract SuccessResponse map(com.hrbatovic.master.micronaut.apigateway.clients.auth.model.SuccessResponse successResponse);

    public abstract UserUpdateCredentialsRequest map(com.hrbatovic.micronaut.master.apigateway.model.UserUpdateCredentialsRequest userUpdateCredentialsRequest);

    public abstract UpdateCredentialsResponse map(com.hrbatovic.master.micronaut.apigateway.clients.auth.model.UpdateCredentialsResponse updateCredentialsResponse);

    public abstract DeleteUserResponse map(com.hrbatovic.master.micronaut.apigateway.clients.user.model.DeleteUserResponse deleteUserResponse);

    public abstract UserProfileResponse map(com.hrbatovic.master.micronaut.apigateway.clients.user.model.UserProfileResponse user);

    public abstract com.hrbatovic.master.micronaut.apigateway.clients.user.model.ListUsersSortParameter map(ListUsersSortParameter sort);

    public abstract UserListResponse map(com.hrbatovic.master.micronaut.apigateway.clients.user.model.UserListResponse userListResponse);

    public abstract com.hrbatovic.master.micronaut.apigateway.clients.user.model.UpdateUserProfileRequest map(UpdateUserProfileRequest updateUserProfileRequest);

    public abstract com.hrbatovic.master.micronaut.apigateway.clients.cart.model.AddCartProductRequest map(AddCartProductRequest addCartProductRequest);

    public abstract CartProductResponse map(com.hrbatovic.master.micronaut.apigateway.clients.cart.model.CartProductResponse cartProductResponse);

    public abstract com.hrbatovic.master.micronaut.apigateway.clients.cart.model.StartCheckoutRequest map(StartCheckoutRequest startCheckoutRequest);

    public abstract CheckoutResponse map(com.hrbatovic.master.micronaut.apigateway.clients.cart.model.CheckoutResponse checkoutResponse);

    public abstract DeleteCartProductResponse map(com.hrbatovic.master.micronaut.apigateway.clients.cart.model.DeleteCartProductResponse deleteCartProductResponse);

    public abstract EmptyCartResponse map(com.hrbatovic.master.micronaut.apigateway.clients.cart.model.EmptyCartResponse emptyCartResponse);

    public abstract CartResponse map(com.hrbatovic.master.micronaut.apigateway.clients.cart.model.CartResponse cartProducts);

    public abstract com.hrbatovic.master.micronaut.apigateway.clients.cart.model.UpdateCartProductRequest map(UpdateCartProductRequest updateCartProductRequest);

    public abstract com.hrbatovic.master.micronaut.apigateway.clients.order.model.GetAllOrdersStatusParameter map(GetAllOrdersStatusParameter status);

    public abstract com.hrbatovic.master.micronaut.apigateway.clients.order.model.GetAllOrdersSortParameter map(GetAllOrdersSortParameter sort);

    public abstract OrderListResponse map(com.hrbatovic.master.micronaut.apigateway.clients.order.model.OrderListResponse allOrders);

    public abstract Order map(com.hrbatovic.master.micronaut.apigateway.clients.order.model.Order orderById);

    public abstract com.hrbatovic.master.micronaut.apigateway.clients.payment.model.CreatePaymentMethodRequest map(CreatePaymentMethodRequest createPaymentMethodRequest);

    public abstract PaymentMethodResponse map(com.hrbatovic.master.micronaut.apigateway.clients.payment.model.PaymentMethodResponse paymentMethod);

    public abstract com.hrbatovic.master.micronaut.apigateway.clients.payment.model.UpdatePaymentMethodRequest map(UpdatePaymentMethodRequest updatePaymentMethodRequest);

    public abstract DeletePaymentMethodResponse map(com.hrbatovic.master.micronaut.apigateway.clients.payment.model.DeletePaymentMethodResponse deletePaymentMethodResponse);

    public abstract PaymentMethodDetailedResponse map(com.hrbatovic.master.micronaut.apigateway.clients.payment.model.PaymentMethodDetailedResponse paymentMethodById);

    public abstract PaymentMethodListResponse map(com.hrbatovic.master.micronaut.apigateway.clients.payment.model.PaymentMethodListResponse paymentMethods);

    public abstract com.hrbatovic.master.micronaut.apigateway.clients.license.model.ListLicensesSortParameter map(ListLicensesSortParameter sort);

    public abstract com.hrbatovic.master.micronaut.apigateway.clients.notification.model.ListNotificationsSortParameter toNotificationSort(ListLicensesSortParameter sort);

    public abstract com.hrbatovic.master.micronaut.apigateway.clients.tracking.model.ListEventsSortParameter toEventSort(ListLicensesSortParameter sort);

    public abstract LicenseResponse map(com.hrbatovic.master.micronaut.apigateway.clients.license.model.LicenseResponse licenseBySerialNumber);

    public abstract LicenseListResponse map(com.hrbatovic.master.micronaut.apigateway.clients.license.model.LicenseListResponse licenseListResponse);

    public abstract NotificationListResponse map(com.hrbatovic.master.micronaut.apigateway.clients.notification.model.NotificationListResponse notificationListResponse);

    public abstract NotificationResponse map(com.hrbatovic.master.micronaut.apigateway.clients.notification.model.NotificationResponse notificationById);

    public abstract Event map(com.hrbatovic.master.micronaut.apigateway.clients.tracking.model.Event eventById);

    public abstract EventListResponse map(com.hrbatovic.master.micronaut.apigateway.clients.tracking.model.EventListResponse eventListResponse);

    public abstract com.hrbatovic.master.micronaut.apigateway.clients.license.model.UpdateLicenseTemplateRequest map(UpdateLicenseTemplateRequest updateLicenseTemplateRequest);

    public abstract LicenseTemplateResponse map(com.hrbatovic.master.micronaut.apigateway.clients.license.model.LicenseTemplateResponse licenseTemplateResponse);

    public abstract LicenseTemplateListResponse map(com.hrbatovic.master.micronaut.apigateway.clients.license.model.LicenseTemplateListResponse licenseTemplateListResponse);

    public abstract DeleteLicenseTemplateResponse map(com.hrbatovic.master.micronaut.apigateway.clients.license.model.DeleteLicenseTemplateResponse deleteLicenseTemplateResponse);

    public abstract com.hrbatovic.master.micronaut.apigateway.clients.license.model.CreateLicenseTemplateRequest map(CreateLicenseTemplateRequest createLicenseTemplateRequest);

    public abstract com.hrbatovic.master.micronaut.apigateway.clients.product.model.CreateProductRequest map(CreateProductRequest createProductRequest);

    public abstract com.hrbatovic.master.micronaut.apigateway.clients.product.model.ListProductsSortParameter toListSort(ListProductsSortParameter sort);

    public abstract com.hrbatovic.master.micronaut.apigateway.clients.product.model.UpdateProductRequest map(UpdateProductRequest updateProductRequest);

    public abstract Category map(com.hrbatovic.master.micronaut.apigateway.clients.product.model.Category categoryById);

    public abstract CategoryListResponse map(com.hrbatovic.master.micronaut.apigateway.clients.product.model.CategoryListResponse categoryListResponse);

    public abstract ProductResponse map(com.hrbatovic.master.micronaut.apigateway.clients.product.model.ProductResponse product);

    public abstract DeleteProductResponse map(com.hrbatovic.master.micronaut.apigateway.clients.product.model.DeleteProductResponse deleteProductResponse);

    public abstract ProductListResponse map(com.hrbatovic.master.micronaut.apigateway.clients.product.model.ProductListResponse productListResponse);
}
