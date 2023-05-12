package com.emontazysta.util;

import java.security.SecureRandom;
import java.util.Random;

public class PasswordGenerator {

    private static final Random random = new SecureRandom();
    private static final String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-+=";

    public static String generatePassword(int length) {
        StringBuilder password = new StringBuilder(length);
        for(int i=0; i<length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }
}
