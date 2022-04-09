package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;
import java.time.LocalDate;
import pl.edu.agh.mwo.invoice.timeutils.TimeUtils;

public class FuelCanister extends LuxuryProduct {

    public FuelCanister(String name, BigDecimal price) {
        super(name, price);
    }

    @Override
    public BigDecimal getPriceWithTax() {
        final int carpentersDayMonth = 3;
        final int carpentersDayDayOfMonth = 19;
        if (TimeUtils.getToday().equals(LocalDate.of(TimeUtils.getToday().getYear(),
                carpentersDayMonth, carpentersDayDayOfMonth))) {
            return getPrice();
        } else {
            return super.getPriceWithTax();
        }
    }
}
