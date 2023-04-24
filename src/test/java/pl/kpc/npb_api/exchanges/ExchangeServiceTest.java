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

    private Double min = 4.6039;
    private Double max = 4.6129;
    Double difference = 0.09220000000000006;

    @Autowired
    private ExchangeService service;

    @Test
    void getAvgExchangeRateOnDateTest(){
        Double test = 4.464;
        assertThat(service.getAvgExchangeRateOnDate("2012-01-02", "eur") == test);
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
        assertEquals(min, result.get(0));
        assertEquals(max, result.get(1));
    }

    @Test
    void getMajorDifferenceTest(){
        // The difference value should be updated to the most recent value
        Double returned = service.getMajorDifference("eur", 3);
        assertEquals(difference, returned);
    }
}