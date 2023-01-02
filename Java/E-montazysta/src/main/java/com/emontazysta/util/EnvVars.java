package com.emontazysta.util;

public class EnvVars {

    public static String get(String key,
                             String defaultValue) {

        String value = System.getenv(key);
        if (value == null) {
            if (defaultValue == null) {
                String msg = String.format("Missing environment variable %s and no default value is given.", key);
                throw new RuntimeException(msg);
            }
            return defaultValue;
        } else {
            return value;
        }
    }

    public static String get(String key) {
        return get(key, null);
    }
}
