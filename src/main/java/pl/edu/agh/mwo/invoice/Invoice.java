package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.*;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Integer invoiceNumber;
    private static Set<Integer> invoiceHolder = new HashSet<>(); //symulacja bazy danych
    private Map<Product, Integer> products = new HashMap<Product, Integer>();
    private final Random random = new Random();

    public Invoice() {
        if (invoiceNumber == null) {
            generateNumber();
            invoiceHolder.add(invoiceNumber);
        }
    }

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }
        products.put(product, quantity);
    }

    public void generateNumber() {
        invoiceNumber = random.nextInt(1000000);
        if (invoiceHolder.contains(invoiceNumber)) {
            generateNumber();
        }
    }

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }

    private BigDecimal getGrossTotal(Map.Entry<Product, Integer> entry) {
        return entry.getKey().getPriceWithTax().multiply(BigDecimal.valueOf(entry.getValue()));
    }

    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String invoiceNumber = "Invoice number: " + getInvoiceNumber() + "\n";
        builder.append(invoiceNumber);
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            String productName = entry.getKey().getName();
            String quantity = String.valueOf(entry.getValue());
            String price = String.valueOf(getGrossTotal(entry));
            builder.append("\t")
                    .append(productName)
                    .append(" ".repeat(20 - productName.length()))
                    .append("Quantity: ")
                    .append(quantity)
                    .append("   ")
                    .append("Price: ")
                    .append(price)
                    .append("\n");
        }
        builder.append("Product quantity: ").append(products.size());
        return builder.toString();
    }
}
