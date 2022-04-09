package pl.edu.agh.mwo.invoice.timeutils;

import java.time.LocalDate;

public final class TimeUtils {

    private TimeUtils() {}

    public static LocalDate getToday() {
        return LocalDate.now();
    }
}
