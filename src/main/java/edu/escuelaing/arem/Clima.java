package edu.escuelaing.arem;

import com.google.gson.Gson;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Clima {
    private static Gson gson;

    public Clima(){
        this.gson = new Gson();
    }

    public static String getClima(String city) throws IOException {
        String res = "";
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=e73560b8e6cc5f694d31429e878663a9");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
                res += inputLine;
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        return gson.toJson(res);
    }

}


