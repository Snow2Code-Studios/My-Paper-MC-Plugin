package org.snow2code.util;

public class SemiSystem {
    public static void Log(String message) {
        String realMsg = String.format(
                "[snow2code] %s",
                message
        );
        System.out.println(realMsg);
    }
}
