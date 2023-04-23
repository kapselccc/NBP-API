package pl.kpc.npb_api.exchanges;

import com.fasterxml.jackson.core.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class ExchangeService {


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

        return getAvgExchangeRateFromJson(response.body()).orElseThrow();
    }

    public Optional<Double> getAvgExchangeRateFromJson(String s){
        Double mid = null;
        try {
            JSONObject jo = (JSONObject) JSONValue.parse(s);
            JSONArray ratesArray = (JSONArray) jo.get("rates");
            JSONObject ratesObject = (JSONObject) ratesArray.get(0);
            mid = (Double) ratesObject.get("mid");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(mid);
    }
}
