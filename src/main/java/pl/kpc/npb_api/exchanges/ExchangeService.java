package pl.kpc.npb_api.exchanges;

import com.fasterxml.jackson.core.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ExchangeService {



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
