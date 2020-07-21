package com.dwp.usersbycity.utils;

import com.dwp.usersbycity.models.User;

public class UserLookupTestsUtils {

    public final static double CITY_LAT = 51.5072;
    public final static double CITY_LON = -0.1275;
    public final static int RADIUS = 50;
    public final static double MILES_PER_METRE = 0.0006213712;
    public final static double MAX_LAT = 90;
    public final static double MAX_LON = 180;
    public final static double MIN_LAT = -90;
    public final static double MIN_LON = -180;

    public static User getUserLivingInCity() {
        User userLivingInCity = new User();
        userLivingInCity.setId("1");
        userLivingInCity.setLatitude("1");
        userLivingInCity.setLongitude("-3");
        return userLivingInCity;
    }

    public static User getUserInCity() {
        User userInCity = new User();
        userInCity.setId("2");
        userInCity.setLatitude("51.7");
        userInCity.setLongitude("-0.132");
        return userInCity;
    }

    public static User getUserNotLinkedToCity() {
        User userNotLinkedToCity = new User();
        userNotLinkedToCity.setId("3");
        userNotLinkedToCity.setLatitude("34");
        userNotLinkedToCity.setLongitude("4");
        return userNotLinkedToCity;
    }
}
