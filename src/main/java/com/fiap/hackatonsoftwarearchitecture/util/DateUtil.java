package com.fiap.hackatonsoftwarearchitecture.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

@UtilityClass
public class DateUtil {

    public LocalDateTime getStartDate(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        return startDate.atStartOfDay();
    }

    public LocalDateTime getEndDate(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate endDate = yearMonth.atEndOfMonth();

        return endDate.atTime(23, 59, 59);
    }
}