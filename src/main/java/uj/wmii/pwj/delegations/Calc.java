package uj.wmii.pwj.delegations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Calc {

    BigDecimal calculate(String name, String start, String end, BigDecimal dailyRate) throws IllegalArgumentException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm VV");
        ZonedDateTime startDate = ZonedDateTime.parse(start, formatter);
        ZonedDateTime endDate = ZonedDateTime.parse(end, formatter);
        Duration duration = Duration.between(startDate, endDate);
        long totalMinutes = duration.toMinutes();

        BigDecimal amount = calculateTotalAmount(dailyRate, totalMinutes);
        return amount;
    }

    private static BigDecimal calculateTotalAmount(BigDecimal dailyRate, long totalMinutes) {
        long fullDays = totalMinutes / (24 * 60);
        long remainingMinutes = totalMinutes % (24 * 60);

        BigDecimal amount = dailyRate.multiply(BigDecimal.valueOf(fullDays));
        if (remainingMinutes > 0) {
            if (remainingMinutes <= 8 * 60) {
                amount = amount.add(dailyRate.divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_UP));
            } else if (remainingMinutes <= 12 * 60) {
                amount = amount.add(dailyRate.divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP));
            } else {
                amount = amount.add(dailyRate);
            }
        }
        return amount;
    }
}
