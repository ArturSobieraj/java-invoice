package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public class LuxuryProduct extends Product {

    public LuxuryProduct(String name, BigDecimal price) {
        super(name, price, new BigDecimal("0.23"));
    }

    @Override
    public BigDecimal getPriceWithTax() {
        BigDecimal basicPrice = super.getPriceWithTax();
        return basicPrice.add(new BigDecimal("5.56"));
    }
}
