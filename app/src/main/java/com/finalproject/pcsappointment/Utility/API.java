package com.finalproject.pcsappointment.Utility;

public class API {
    public static final String STATUS_OK = "Ok";
    public static final String STATUS_ERROR = "Error";

    public static final String DOMAIN = "http://192.168.0.185:48544";


    //CALLBACK
    private static final String BASE_URL = "/WebApplication2/Mobile/Application/";
    public static final String DOMAIN_URL = DOMAIN + BASE_URL;


    // other api's
    public static final String LOGIN_URL = DOMAIN_URL + "Login/";
    public static final String REGISTRATION_URL = DOMAIN_URL + "Registration/";


    public static final String LOGOUT_URL = "siconLogoutUser";
    public static final String SYNC_USER_INVOICE_URL = "siconSyncUserInvoice";
    public static final String SYNC_USER_PENDING_SALE_URL = "siconUpdateUserPendingSale";
    public static final String SYNC_USER_PENDING_REJECTION_URL = "siconUpdateUserPendingRejection";
    public static final String AUTO_SYNC_INVOICE_URL = "siconAutoSyncInvoiceSaleRej";
    public static final String SYNC_REFRESH_APP_DATA_URL = "siconSyncRefreshData";
    public static final String UPDATE_SALE_DATA_URL = "siconUpdateSale";
    public static final String SYNC_URL = "siconSyncRoute";     //using rollback
    public static final String CREDIT_ADJUST_SUPERVISOR_LOGIN_URL = "creditAdjustSupervisorLogin";
    public static final String CREDIT_ADJUST_URL = "updateCustomerPayment";
    public static final String VAN_SUPERVISOR_LOGIN_URL = "siconVanSupervisorLogin";
    public static final String ROUTE_SUPERVISOR_LOGIN_URL = "siconRouteSupervisorLogin";
    public static final String VAN_CLOSE_URL = "siconVanClose";     //using rollback
    public static final String FINAL_PAYMENT_URL = "siconFinalPayment";     //using rollback
    public static final String FINAL_PAYMENT_PRINT_STATEMENT_URL = "siconPrintStatement";
    // upi related
    public static final String GENERATE_PAYMENT_QR_URL = "generatePaymentQrCode";
    public static final String CHECK_UPI_PAYMENT_STATUS_URL = "checkUpiPaymentStatus";


}
