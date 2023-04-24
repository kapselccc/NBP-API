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
        // change the min, max values to the most recent
        ResponseEntity<List<Double>> response = controller.getMinMaxExchangeRate("eur", 3);
        assertThat(response.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    void getMinMaxExchangeRateTestException(){
        ResponseEntity<List<Double>> response = controller.getMinMaxExchangeRate("pln", 3);
        assertThat(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));

    }

    @Test
    void getMajorDifferenceTest(){
        ResponseEntity<Double> response = controller.getMajorDifference("eur", 3);
        assertThat(response.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    void getMajorDifferenceTestException1(){
        ResponseEntity<Double> response = controller.getMajorDifference("pln", 3);
        assertThat(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }

    @Test
    void getMajorDifferenceTestException2(){
        ResponseEntity<Double> response = controller.getMajorDifference("eur", 0);
        assertThat(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }

}