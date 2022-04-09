package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import pl.edu.agh.mwo.invoice.timeutils.TimeUtils;

public class ProductTest {

    @Test
    public void testProductNameIsCorrect() {
        Product product = new OtherProduct("buty", new BigDecimal("100.0"));
        Assert.assertEquals("buty", product.getName());
    }

    @Test
    public void testProductPriceAndTaxWithDefaultTax() {
        Product product = new OtherProduct("Ogorki", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("100"), Matchers.comparesEqualTo(product.getPrice()));
        Assert.assertThat(new BigDecimal("0.23"), Matchers.comparesEqualTo(product.getTaxPercent()));
    }

    @Test
    public void testProductPriceAndTaxWithDairyProduct() {
        Product product = new DairyProduct("Szarlotka", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("100"), Matchers.comparesEqualTo(product.getPrice()));
        Assert.assertThat(new BigDecimal("0.08"), Matchers.comparesEqualTo(product.getTaxPercent()));
    }

    @Test
    public void testProductPriceAndTaxWithBottleOfWine() {
        Product product = new BottleOfWine("Kadarka", new BigDecimal("150"));
        Assert.assertEquals(new BigDecimal("150"), product.getPrice());
        Assert.assertEquals(new BigDecimal("0.23"), product.getTaxPercent());
        Assert.assertEquals(new BigDecimal("190.06"), product.getPriceWithTax());
    }

    @Test
    public void testProductPriceAndTaxWithFuelCanister() {
        Product product = new FuelCanister("E95", new BigDecimal("100"));
        Assert.assertEquals(new BigDecimal("100"), product.getPrice());
        Assert.assertEquals(new BigDecimal("0.23"), product.getTaxPercent());
        Assert.assertEquals(new BigDecimal("128.56"), product.getPriceWithTax());
    }

    @Test
    public void testProductPriceAndTaxWithFuelCanisterOnCarpentersDay() {
        int currentYear = LocalDate.now().getYear();
        try (MockedStatic<TimeUtils> utils = Mockito.mockStatic(TimeUtils.class)) {
            utils.when(TimeUtils::getToday).thenReturn(LocalDate.of(currentYear, 3, 19));
            FuelCanister product = new FuelCanister("E95", new BigDecimal("100"));
            Assert.assertEquals(new BigDecimal("100"), product.getPrice());
            Assert.assertEquals(new BigDecimal("0.23"), product.getTaxPercent());
            Assert.assertEquals(new BigDecimal("100"), product.getPriceWithTax());
        }
    }

    @Test
    public void testPriceWithTax() {
        Product product = new DairyProduct("Oscypek", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("108"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithNullName() {
        new OtherProduct(null, new BigDecimal("100.0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithEmptyName() {
        new TaxFreeProduct("", new BigDecimal("100.0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithNullPrice() {
        new DairyProduct("Banany", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithNegativePrice() {
        new TaxFreeProduct("Mandarynki", new BigDecimal("-1.00"));
    }
}
