package pl.kpc.npb_api.exchanges;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExchangeControllerTest {

    @Autowired
    private ExchangeController controller;

    @Test
    void getMinMaxExchangeRateTest(){
        Double min = 4.6039;
        Double max = 4.6278;
        ResponseEntity<List<Double>> response = controller.getMinMaxExchangeRate("eur", 3);
        assertEquals(min, response.getBody().get(0));
        assertEquals(max, response.getBody().get(1));

    }

    @Test
    void getMinMaxExchangeRateTestException(){
        ResponseEntity<List<Double>> response = controller.getMinMaxExchangeRate("pln", 3);
        assertThat(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));

    }

}