package pl.kpc.npb_api.exchanges;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/exchanges")
public class ExchangeController {
    private final ExchangeService service;

    @Autowired
    public ExchangeController(ExchangeService service) {
        this.service = service;
    }


    @Operation(summary = "Get average exchange rate of for a currency on requested date")
    @GetMapping("avg/{code}/{date}")
    public ResponseEntity<Double> getAvgExchangeRate(@PathVariable("code") @Parameter(name = "code",
            description = "Code of the currency") String code,
                                                     @PathVariable("date") @Parameter(name = "date",
                                                             description = "Date in format \"YYYY-MM-DD\"") String date){
        Double avg;
        try {
            avg = service.getAvgExchangeRateOnDate(date, code);
        }
        catch (NoSuchElementException e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(avg);
    }

    @Operation(summary = "Get minimum and maximum exchange rate of for a currency from n quotations")
    @GetMapping("minmax/{code}/{n}")
    public ResponseEntity<List<Double>> getMinMaxExchangeRate(@PathVariable("code") @Parameter(name = "code",
            description = "Code of the currency") String code,
                                                              @PathVariable("n") @Parameter(name = "n",
                                                                      description = "Number of last quotations") int n){
        List<Double> minMax;
        try{
            minMax = service.getMinMaxExchangeRate(code, n);
        }catch (IllegalArgumentException | NoSuchElementException e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(minMax);

    }
    @Operation(summary = "Get the major difference between ask and bid rate from last n quotations")
    @GetMapping("difference/{code}/{n}")
    public ResponseEntity<Double> getMajorDifference(@PathVariable("code") @Parameter(name = "code",
            description = "Code of the currency") String code,
                                                     @PathVariable("n") @Parameter(name = "n",
                                                             description = "Number of last quotations") int n){
        Double majorDifference;
        try{
            majorDifference = service.getMajorDifference(code, n);
        }catch (IllegalArgumentException | NoSuchElementException e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(majorDifference);
    }
}
