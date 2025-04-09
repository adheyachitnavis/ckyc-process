package com.docuscan.ckyc.util;

public class NumberUtil {

    public static int parseInt(String str) {
        if (str == null || str.trim().isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(str.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
