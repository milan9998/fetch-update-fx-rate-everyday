package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashMap;

public class ListOfCurrencies {
    @JsonProperty("data")
    private LinkedHashMap<String, Double> currencies;

    public ListOfCurrencies() {

    }

    public LinkedHashMap<String, Double> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(LinkedHashMap<String, Double> currencies) {
        this.currencies = currencies;
    }

    @Override
    public String toString() {
        return "ListOfCurrencies{" +
                "data=" + currencies +
                '}';
    }
}
