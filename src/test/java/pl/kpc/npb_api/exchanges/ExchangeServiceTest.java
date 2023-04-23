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
        assertThat(service.getAvgExchangeRateOnDate("2012-01-02", "eur") == 4.464);
    }

    @Test
    void getAvgExchangeRateOnDateTestThrows() {
        assertThrows(NoSuchElementException.class, () -> {
            service.getAvgExchangeRateOnDate("2012-01-02", "pln");
        });
    }

    @Test
    void getMinMaxExchangeRateTest() {
        // The min, max values should be updated to the latest values
        Double min = 4.6039;
        Double max = 4.6278;
        List<Double> result = service.getMinMaxExchangeRate("eur", 3);
        assertEquals(result.get(0), min);
        assertEquals(result.get(1), max);

    }
}