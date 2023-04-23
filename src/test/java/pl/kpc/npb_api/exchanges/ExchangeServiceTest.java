package pl.kpc.npb_api.exchanges;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExchangeServiceTest {

    @Autowired
    private ExchangeService service;

    @Test
    void getAvgExchangeRateFromJsonTest(){
        String json = "{\"table\":\"A\",\"currency\":\"euro\",\"code\":\"EUR\",\"rates\":[{\"no\":\"1/A/NBP/2012\"," +
            "\"effectiveDate\":\"2012-01-02\",\"mid\":4.4640}]}";
        assertEquals(4.464,service.getAvgExchangeRateFromJson(json).get());
    }

    @Test
    void getAvgExchangeRateFromJsonTestNull(){
        String json = "{random string}";
        assertEquals(null,service.getAvgExchangeRateFromJson(json).orElse(null));
    }


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
}