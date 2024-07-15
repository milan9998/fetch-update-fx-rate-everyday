package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class ApiConnect extends DbConnection {

    public ApiConnect() throws IOException {
        execute();
    }

    public void execute() throws IOException {
        URL urlObj = new URL("https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_2CcOM6tDixrT88a4FFezBt03Msrwhqv6pyp6bAdC");
        HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code : " + responseCode);
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            StringBuilder sb = new StringBuilder();
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNext()) {
                sb.append(scanner.nextLine());
            }
            // System.out.println(sb);
            ObjectMapper objectMapper = new ObjectMapper();

            objectMapper.getTypeFactory().clearCache();
            ListOfCurrencies listOfCurrencies = objectMapper.readValue(sb.toString(), ListOfCurrencies.class);
            //MUST BE EMPTY CONSTRUCTOR OF LISTOFCURRENCIES BECAUSE CANT INITIALIZE JACKSON OBJECT
            try {
                for (Map.Entry<String, Double> m : listOfCurrencies.getCurrencies().entrySet()) {
                    String s1 = m.getKey();
                    Double d1 = m.getValue();
                    //insertCurrency(s1, d1);
                    updateCurrency(d1,s1);
                }
                System.out.println("Data inserted successfully.");
            } catch (SQLException ex) {
                System.out.println("Error inserting data into database: " + ex.getMessage());
            }
            System.out.println(listOfCurrencies);
        } else {
            System.out.println("Error in sending a GET request");
        }
    }
}
