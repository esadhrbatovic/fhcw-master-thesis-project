package com.hrbat.quarkus.master.apigateway.mapper;

import com.hrbat.quarkus.master.apigateway.model.*;
import com.hrbat.quarkus.master.apigateway.model.auth.model.*;
import com.hrbat.quarkus.master.apigateway.model.cart.model.*;
import com.hrbat.quarkus.master.apigateway.model.license.model.*;
import com.hrbat.quarkus.master.apigateway.model.notification.model.NotificationApiNotificationListResponse;
import com.hrbat.quarkus.master.apigateway.model.notification.model.NotificationApiNotificationResponse;
import com.hrbat.quarkus.master.apigateway.model.order.model.OrderApiOrder;
import com.hrbat.quarkus.master.apigateway.model.order.model.OrderApiOrderListResponse;
import com.hrbat.quarkus.master.apigateway.model.payment.model.*;
import com.hrbat.quarkus.master.apigateway.model.product.model.*;
import com.hrbat.quarkus.master.apigateway.model.tracking.model.TrackingApiEvent;
import com.hrbat.quarkus.master.apigateway.model.tracking.model.TrackingApiEventListResponse;
import com.hrbat.quarkus.master.apigateway.model.user.model.UserApiDeleteUserResponse;
import com.hrbat.quarkus.master.apigateway.model.user.model.UserApiUpdateUserProfileRequest;
import com.hrbat.quarkus.master.apigateway.model.user.model.UserApiUserListResponse;
import com.hrbat.quarkus.master.apigateway.model.user.model.UserApiUserProfileResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public abstract class MapUtil {
    public abstract AuthApiLoginRequest map(LoginRequest loginRequest);

    public abstract LoginResponse map(AuthApiLoginResponse login);

    public abstract AuthApiRegisterRequest map(RegisterRequest registerRequest);

    public abstract RegisterResponse map(AuthApiRegisterResponse register);

    public abstract AuthApiAdminUpdateCredentialsRequest map(AdminUpdateCredentialsRequest adminUpdateCredentialsRequest);

    public abstract SuccessResponse map(AuthApiSuccessResponse authApiSuccessResponse);

    public abstract AuthApiUserUpdateCredentialsRequest map(UserUpdateCredentialsRequest userUpdateCredentialsRequest);

    public abstract UpdateCredentialsResponse map(AuthApiUpdateCredentialsResponse authApiUpdateCredentialsResponse);

    public abstract DeleteUserResponse map(UserApiDeleteUserResponse userApiDeleteUserResponse);

    public abstract UserProfileResponse map(UserApiUserProfileResponse user);

    public abstract OrderListResponse map(OrderApiOrderListResponse userOrders);

    public abstract UserListResponse map(UserApiUserListResponse userApiUserListResponse);

    public abstract UserApiUpdateUserProfileRequest map(UpdateUserProfileRequest updateUserProfileRequest);

    public abstract ProductApiCreateProductRequest map(CreateProductRequest createProductRequest);

    public abstract ProductResponse map(ProductApiProductResponse product);

    public abstract DeleteProductResponse map(ProductApiDeleteProductResponse productApiDeleteProductResponse);

    public abstract ProductApiUpdateProductRequest map(UpdateProductRequest updateProductRequest);

    public abstract ProductListResponse map(ProductApiProductListResponse productApiProductListResponse);

    public abstract CartApiAddCartProductRequest map(AddCartProductRequest addCartProductRequest);

    public abstract CartProductResponse map(CartApiCartProductResponse cartApiCartProductResponse);

    public abstract CartApiStartCheckoutRequest map(StartCheckoutRequest startCheckoutRequest);

    public abstract CheckoutResponse map(CartApiCheckoutResponse cartApiCheckoutResponse);

    public abstract DeleteCartProductResponse map(CartApiDeleteCartProductResponse cartApiDeleteCartProductResponse);

    public abstract EmptyCartResponse map(CartApiEmptyCartResponse cartApiEmptyCartResponse);

    public abstract CartResponse map(CartApiCartResponse cartProducts);

    public abstract CartApiUpdateCartProductRequest map(UpdateCartProductRequest updateCartProductRequest);

    public abstract Order map(OrderApiOrder orderById);

    public abstract PaymentApiCreatePaymentMethodRequest map(CreatePaymentMethodRequest createPaymentMethodRequest);

    public abstract PaymentMethodResponse map(PaymentApiPaymentMethodResponse paymentMethod);

    public abstract DeletePaymentMethodResponse map(PaymentApiDeletePaymentMethodResponse paymentApiDeletePaymentMethodResponse);

    public abstract PaymentMethodDetailedResponse map(PaymentApiPaymentMethodDetailedResponse paymentMethodById);

    public abstract PaymentMethodListResponse map(PaymentApiPaymentMethodListResponse paymentMethods);

    public abstract PaymentApiUpdatePaymentMethodRequest map(UpdatePaymentMethodRequest updatePaymentMethodRequest);

    public abstract LicenseResponse map(LicenseApiLicenseResponse licenseBySerialNumber);

    public abstract LicenseListResponse map(LicenseApiLicenseListResponse licenseApiLicenseListResponse);

    public abstract LicenseApiCreateLicenseTemplateRequest map(CreateLicenseTemplateRequest createLicenseTemplateRequest);

    public abstract LicenseTemplateResponse map(LicenseApiLicenseTemplateResponse licenseTemplate);

    public abstract DeleteLicenseTemplateResponse map(LicenseApiDeleteLicenseTemplateResponse licenseApiDeleteLicenseTemplateResponse);

    public abstract LicenseTemplateListResponse map(LicenseApiLicenseTemplateListResponse licenseApiLicenseTemplateListResponse);

    public abstract LicenseApiUpdateLicenseTemplateRequest map(UpdateLicenseTemplateRequest updateLicenseTemplateRequest);

    public abstract NotificationResponse map(NotificationApiNotificationResponse notificationById);

    public abstract NotificationListResponse map(NotificationApiNotificationListResponse notificationApiNotificationListResponse);

    public abstract Event map(TrackingApiEvent eventById);

    public abstract EventListResponse map(TrackingApiEventListResponse trackingApiEventListResponse);
}
