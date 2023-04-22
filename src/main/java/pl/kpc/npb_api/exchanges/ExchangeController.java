package pl.kpc.npb_api.exchanges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.NoSuchElementException;

@RestController
public class ExchangeController {
    private final ExchangeService service;

    @Autowired
    public ExchangeController(ExchangeService service) {
        this.service = service;
    }

    public double getAvgExchangeRateOnDate(String date, String code) throws NoSuchElementException {
        HttpRequest request = HttpRequest.newBuilder(
                URI.create(String.format("http://api.nbp.pl/api/exchangerates/rates/a/%s/%s/",code,date)))
                .header("accept","application/json" )
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = null;
        try{
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }

        return service.getAvgExchangeRateFromJson(response.body()).orElseThrow();
    }
}
