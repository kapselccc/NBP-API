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

    // these values should be updated to the most recent values
    private Double min = 4.6039;
    private Double max = 4.6129;
    Double difference = 0.09220000000000006;

    @Test
    void getMinMaxExchangeRateTest(){
        // change the min, max values to the most recent
        ResponseEntity<List<Double>> response = controller.getMinMaxExchangeRate("eur", 3);
        assertEquals(min, response.getBody().get(0));
        assertEquals(max, response.getBody().get(1));

    }

    @Test
    void getMinMaxExchangeRateTestException(){
        ResponseEntity<List<Double>> response = controller.getMinMaxExchangeRate("pln", 3);
        assertThat(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));

    }

    @Test
    void getMajorDifferenceTest(){
        ResponseEntity<Double> response = controller.getMajorDifference("eur", 3);
        assertEquals(difference,response.getBody());
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