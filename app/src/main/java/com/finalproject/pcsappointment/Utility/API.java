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
    public static final String USER_PROFILE_URL = DOMAIN_URL + "UserProfile/";
    public static final String CREATE_APPOINTMENT_SLOT_URL = DOMAIN_URL + "createAppointmentSlot/";
    public static final String CONSULTANT_APPOINTMENT_SLOTS_URL = DOMAIN_URL + "consultantAppointmentSlots/";
    public static final String ALL_APPOINTMENT_SLOTS_URL = DOMAIN_URL + "allAppointmentSlots/";
    public static final String REQUEST_APPOINTMENT_URL = DOMAIN_URL + "requestAppointment/";
    public static final String STUDENT_REQUESTS_URL = DOMAIN_URL + "studentRequests/";
    public static final String STUDENT_REQUEST_RESPONSE_URL = DOMAIN_URL + "studentRequestResponse/";
    public static final String RESPOND_TO_REQUEST_URL = DOMAIN_URL + "respondToRequest/";



}
