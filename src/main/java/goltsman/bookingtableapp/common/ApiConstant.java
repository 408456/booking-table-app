package goltsman.bookingtableapp.common;

public class ApiConstant {
    public static final String ID = "/{id}";
    public static final String ME_URL = "/me";
    public static final String SIGN_IN_URL = "/sign-in";
    public static final String SIGN_UP_URL = "/sign-up";
    public static final String REFRESH_URL = "/refresh";
    public static final String RESTAURANT_CONTROLLER_URL = "/api/v1/restaurants";
    public static final String CUISINE_CONTROLLER_URL = "/api/v1/cuisines";
    public static final String REVIEW_CONTROLLER_URL = "/api/v1/reviews";
    public static final String TABLE_CONTROLLER_URL = "/api/v1/tables";
    public static final String BOOKING_CONTROLLER_URL = "/api/v1/booking";
    public static final String AUTH_CONTROLLER_URL = "/auth";
    public static final String USER_CONTROLLER_URL = "/api/v1/users";

    public static final String RESTAURANT_BY_CUISINE = "/cuisine/{cuisineId}";
    public static final String RESTAURANT_BOOKINGS = "/restaurants/{restaurantId}/bookings";
    public static final String USER_BOOKINGS = "/user/{userId}";
    public static final String MY_BOOKINGS = "/users/me/bookings";
    public static final String RESTAURANT_TABLES = "/restaurants/{restaurantId}/tables";
    public static final String RESTAURANT_TABLES_AVAILABLE = "/restaurants/{restaurantId}/tables/available";
    public static final String AVAILABLE_TABLES = "/restaurant/{restaurantId}/available";
}
