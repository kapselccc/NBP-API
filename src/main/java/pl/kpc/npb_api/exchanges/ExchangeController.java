package pl.kpc.npb_api.exchanges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/exchanges")
public class ExchangeController {
    private final ExchangeService service;

    @Autowired
    public ExchangeController(ExchangeService service) {
        this.service = service;
    }


    @GetMapping("avg/{code}/{date}")
    public ResponseEntity<Double> getAvgExchangeRate(@PathVariable("code") String code,
                                                     @PathVariable("date") String date){
        Double avg;
        try {
            avg = service.getAvgExchangeRateOnDate(date, code);
        }
        catch (NoSuchElementException e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(avg);
    }

    @GetMapping("minmax/{code}/{n}")
    public ResponseEntity<List<Double>> getMinMaxExchangeRate(@PathVariable("code") String code,
                                                              @PathVariable("n") int n){
        List<Double> minMax ;
        try{
            minMax = service.getMinMaxExchangeRate(code, n);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(minMax);

    }
}
