package util;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Validate {
    public static boolean usernames (String username) {
        String[] arrUser = username.split("");
        for(String str : arrUser) {
            if (str.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean password (String password) {
        if (password.length() < 6) {
            return false;
        }
        return usernames(password);
    }

    public static Date inputReceiveAt(Date createdAt) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 4);
        return calendar.getTime();
    }
}