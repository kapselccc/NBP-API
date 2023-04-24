package pl.kpc.npb_api.exchanges;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExchangeServiceTest {


    @Autowired
    private ExchangeService service;

    @Test
    void getAvgExchangeRateOnDateTest(){
        Object test = service.getAvgExchangeRateOnDate("2012-01-02", "eur");
        assertTrue(test != null);
        assertTrue((Double)test > 0.0);
    }

    @Test
    void getAvgExchangeRateOnDateTestThrows() {
        assertThrows(NoSuchElementException.class, () -> {
            service.getAvgExchangeRateOnDate("2012-01-02", "pln");
        });
    }

    @Test
    void getMinMaxExchangeRateTest() {
        // The min, max values should be updated to the most recent values
        List<Double> result = service.getMinMaxExchangeRate("eur", 3);
        Object min = result.get(0);
        Object max = result.get(1);
        assertThat(min != null);
        assertThat((Double) min > 0.0);
        assertThat(max != null);
        assertThat((Double) max > 0.0);

    }

    @Test
    void getMajorDifferenceTest(){
        // The difference value should be updated to the most recent value
        Object returned = service.getMajorDifference("eur", 3);
        assertThat(returned != null);
        assertThat((Double) returned > 0.0);
    }
}