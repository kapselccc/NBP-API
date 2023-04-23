package pl.kpc.npb_api.exchanges;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;


@Service
public class ExchangeService {


    public double getAvgExchangeRateOnDate(String date, String code) throws NoSuchElementException {
        String response = sendGetRequest(String.format("http://api.nbp.pl/api/exchangerates/rates/a/%s/%s/",
                code,date));
        return getAvgExchangeRateFromJson(response).orElseThrow();
    }

    public List<Double> getMinMaxExchangeRate(String code, int n) throws IllegalArgumentException{
        if(n > 255)
        {
            throw new IllegalArgumentException("N must be <= 255");
        }else if( n <= 0){
            throw new IllegalArgumentException("N must be positive number <= 255");
        }
        String response = sendGetRequest(String.format("http://api.nbp.pl/api/exchangerates/rates/a/%s/last/%d/",
                code, n));

        List<Double> list = getManyExchangeRateFromJson(response);
        Collections.sort(list);
        return new ArrayList<>(List.of(list.get(0),list.get(list.size() - 1) ));
    }

    private Optional<Double> getAvgExchangeRateFromJson(String s){
        Double mid = null;
        try {
            JSONObject jo = new JSONObject(s);
            JSONArray ratesArray = (JSONArray) jo.get("rates");
            JSONObject ratesObject = (JSONObject) ratesArray.get(0);
            mid = (Double) ratesObject.get("mid");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(mid);
    }

    private List<Double> getManyExchangeRateFromJson(String s){
        List<Double> list = new ArrayList<>();
        try {
            JSONObject jo = new JSONObject(s);
            JSONArray ratesArray = (JSONArray) jo.get("rates");
            for(int i = 0;i < ratesArray.length();i++){
                JSONObject ratesObject = (JSONObject) ratesArray.get(i);
                list.add((Double) ratesObject.get("mid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private String sendGetRequest(String uri){
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create(uri))
                .header("accept","application/json" )
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = null;
        try{
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }

        return response.body();
    }

}
