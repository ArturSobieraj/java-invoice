package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Collection<Product> products;
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal total;


    public Invoice() {
        subtotal = BigDecimal.ZERO;
        products = new ArrayList<>();
        tax = BigDecimal.ZERO;
        total = BigDecimal.ZERO;
    }

    public void addProduct(Product product) {
        products.add(product);
        subtotal = subtotal.add(product.getPrice());
        total = total.add(product.getPriceWithTax());
        tax = tax.add(product.getPrice().multiply(product.getTaxPercent()));
    }

    public void addProduct(Product product, Integer quantity) {
        if (quantity == 0 || quantity < 0) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < quantity; i++) {
            addProduct(product);
        }
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public BigDecimal getTotal() {
        if (subtotal.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        } else {
            return total;
        }
    }
}
