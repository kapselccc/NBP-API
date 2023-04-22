package pl.kpc.npb_api.exchanges;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExchangeControllerTest {

    @Autowired
    private ExchangeController controller;

    @Test
    void getAvgExchangeRateOnDateTest(){
        assertThat(controller.getAvgExchangeRateOnDate("2012-01-02", "eur") == 4.464);
    }

    @Test
    void getAvgExchangeRateOnDateTestThrows(){
        assertThrows(NoSuchElementException.class, () -> {
            controller.getAvgExchangeRateOnDate("2012-01-02", "pln");
        });
    }
}