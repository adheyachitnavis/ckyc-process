package com.docuscan.ckyc.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static String todayStr() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
    }

    public static String todayStrWithHiphen() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public static Instant toDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy")).atStartOfDay().toInstant(java.time.ZoneOffset.UTC);

    }
}
