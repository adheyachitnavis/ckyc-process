package com.docuscan.ckyc.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
            .ofPattern("dd-MM-yyyy")
            .withZone(ZoneOffset.UTC);

    public static String todayStr() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
    }

    public static String todayStrWithHiphen() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public static Instant toDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy")).atStartOfDay().toInstant(java.time.ZoneOffset.UTC);

    }

    public static CharSequence fromDate(Instant date) {
        return date != null ? DATE_FORMATTER.format(date) : "";
    }
}
