package pl.kpc.npb_api.exchanges;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;


@Service
public class ExchangeService {


    public Double getAvgExchangeRateOnDate(String date, String code) throws NoSuchElementException {
        String response = sendGetRequest(String.format("http://api.nbp.pl/api/exchangerates/rates/a/%s/%s/",
                code,date));
        Double value = getAvgExchangeRateFromJson(response).orElse(null);
        if (value == null){
            throw new NoSuchElementException("Value not found");
        }
        return value;
    }

    public List<Double> getMinMaxExchangeRate(String code, int n) throws IllegalArgumentException,
            NoSuchElementException {
        if(n > 255)
        {
            throw new IllegalArgumentException("N must be <= 255");
        }else if( n <= 0){
            throw new IllegalArgumentException("N must be positive number <= 255");
        }
        String response = sendGetRequest(String.format("http://api.nbp.pl/api/exchangerates/rates/a/%s/last/%d/",
                code, n));

        List<Double> list = getManyExchangeRateFromJson(response);
        if(list.isEmpty()){
            throw new NoSuchElementException("No exchange rate found");
        }
        Collections.sort(list);
        return new ArrayList<>(List.of(list.get(0),list.get(list.size() - 1) ));
    }

    public Double getMajorDifference(String code, int n) throws  IllegalArgumentException,
            NoSuchElementException{
        if(n > 255)
        {
            throw new IllegalArgumentException("N must be <= 255");
        }else if( n <= 0){
            throw new IllegalArgumentException("N must be positive number <= 255");
        }
        String response = sendGetRequest(String.format("http://api.nbp.pl/api/exchangerates/rates/c/%s/last/%d/",
                code, n));
        List<Double> differences = getDifferencesFromJson(response);
        if(differences.isEmpty()){
            throw new NoSuchElementException("No asks or bids found");
        }
        Collections.sort(differences);
        return differences.get(differences.size() - 1);
    }

    private Optional<Double> getAvgExchangeRateFromJson(String s){
        Double mid = null;
        try {
            JSONObject jo = new JSONObject(s);
            JSONArray ratesArray = (JSONArray) jo.get("rates");
            JSONObject ratesObject = (JSONObject) ratesArray.get(0);
            mid = (Double) ratesObject.get("mid");
        } catch (Exception ignored) {
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
        } catch (Exception ignored) {
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

    private List<Double> getDifferencesFromJson(String s){
        List<Double> differences = new ArrayList<>();
        try{
            Double bid = 0.0;
            Double ask = 0.0;
            JSONObject jo = new JSONObject(s);
            JSONArray ratesArray = (JSONArray) jo.get("rates");
            for(int i =0;i < ratesArray.length();i++){
                JSONObject ratesObject = (JSONObject) ratesArray.get(i);
                bid = (Double) ratesObject.get("bid");
                ask = (Double) ratesObject.get("ask");
                differences.add(ask - bid);
            }
        } catch (Exception ignored) {
        }
        return differences;
    }

}
